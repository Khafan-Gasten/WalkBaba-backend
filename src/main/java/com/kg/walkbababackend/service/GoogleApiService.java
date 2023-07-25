package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.*;
import com.kg.walkbababackend.model.openai.DTO.MapsApi.ExportLinkDTO;
import com.kg.walkbababackend.model.openai.DTO.MapsApi.ImagesApi.GMapResponseDTO;
import com.kg.walkbababackend.model.openai.DTO.MapsApi.directionsApi.Leg;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import com.kg.walkbababackend.model.openai.DTO.MapsApi.directionsApi.DirectionsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleApiService {

    @Qualifier("googleRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${googleMap.api.key}")
    String GOOGLE_MAPS_API_KEY ;

    @Value("${googleMap.api.url}")
    String GOOGLE_API_URL_BASE ;

    @Value("${googleMap.maps.url}")
    String GOOGLE_MAPS_EXPORT_URL_BASE ;

    @Value("${googleMap.imageMaxWidth}")
    Long imageMaxWidth;

    Long SHORTEST_WALK_TIME_MIN = 20L;
    Long LONGEST_WALK_TIME_MIN = 300L;
    Double SHORTEST_WALK_DIST_KM = 1.0;
    Double LONGEST_WALK_DIST_KM = 12.0;

    public List<RouteToFrontEndDTO> getRoutesToRender(List<OpenAIRouteDTO> routes, UserRequestDTO requestDTO) {
        return routes.stream()
                .map(route -> getOneRoute(route, requestDTO))
                .filter(route -> checkIfRouteIsValid(route.distance(), route.durationInMin()))
                .toList();
    }

    public boolean checkIfRouteIsValid(Double distance, Long duration) {
        return duration <= LONGEST_WALK_TIME_MIN && duration >= SHORTEST_WALK_TIME_MIN
                && distance <= LONGEST_WALK_DIST_KM && distance >= SHORTEST_WALK_DIST_KM;
    }

    public RouteToFrontEndDTO getOneRoute(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO) {
        String requestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO, false);
        String reverseRequestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO, true);
        ExportLinkDTO exportLinks = urlBuilders(requestUrl, reverseRequestUrl);

        DirectionsResponseDTO directions = callDirectionsApi(routeDTO, requestDTO, requestUrl);
        Double totalDist = calculateDistance(directions.routes().get(0).legs);
        Long totalDur = calculateDuration(directions.routes().get(0).legs);     //Why is it get 0?
        List<List<String>> imageUrls = directions.geocodedWaypointList().stream()
                .map(waypoint -> getPlaceImageUrl(waypoint.place_id))
                .toList();
        List<WaypointDTO> newWaypointDTOS = new ArrayList<>();
        List<WaypointDTO> waypoints = routeDTO.waypoints();
        for (int i = 0; i < waypoints.size(); i++) {
            newWaypointDTOS.add(waypoints.get(i).withImageLink(imageUrls.get(i)));
        }
        return new RouteToFrontEndDTO(routeDTO, requestDTO, totalDist, totalDur, exportLinks, newWaypointDTOS);
    }

    private String startExportMapsUrlBuilder(String exportLink) {
        String startingPointUrl = exportLink.split("&origin=")[1].split("&destination=")[0];
        String mapToStartingPoint = exportLink.replace("&origin=" + startingPointUrl,"")
                .replace("&waypoints=", ("&waypoints=" + startingPointUrl));
        return mapToStartingPoint;
    }

    public double calculateDistance(List<Leg> legs) {
        return Math.round(((double) (legs.stream().mapToInt(leg -> leg.distance.value).sum()))/100)/10; //Need to be careful with rounding
    }

    public long calculateDuration(List<Leg> legs) {
        return  ((long) (((legs.stream().mapToInt(leg -> leg.duration.value).sum()))/60));
    }

    public DirectionsResponseDTO callDirectionsApi(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO, String requestUrl) {
        URI builtURI = URI.create(requestUrl);
        System.out.println("URI: " + builtURI);
        DirectionsResponseDTO response = restTemplate.getForObject(builtURI, DirectionsResponseDTO.class);
        return response;
    }

    public ExportLinkDTO urlBuilders(String requestUrl, String reverseRequestUrl) {
        String exportLink = exportMapsUrlBuilder(requestUrl);
        String flippedWaypointsUrl = exportMapsUrlBuilder(reverseRequestUrl);

        String startExportLink = startExportMapsUrlBuilder(exportLink) ;
        String endExportLink = startExportMapsUrlBuilder(flippedWaypointsUrl);

        return new ExportLinkDTO(exportLink, startExportLink, endExportLink);
    }

    public String directionApiUrlRequestBuilder(OpenAIRouteDTO route, UserRequestDTO requestDTO, Boolean reverseWaypoints) {
        List<WaypointDTO> waypointsDTOList = route.waypoints();
        List<String> waypointsList = new ArrayList<>();

        if (reverseWaypoints == true){
            Collections.reverse(waypointsDTOList);
        }

        for (int i = 0; i < waypointsDTOList.size(); i++) {
            if (i == 0 || i == waypointsDTOList.size()-1) {
                waypointsList.add(addCityAndCountryDetails(waypointsDTOList.get(i).name(), requestDTO, false));
                continue;
            }
            waypointsList.add(addCityAndCountryDetails(waypointsDTOList.get(i).name(), requestDTO, true));
        }

        String origin = waypointsList.remove(0);
        String destination = waypointsList.remove(waypointsList.size()-1);

        String waypointsJoined = String.join("", waypointsList);
        String waypoints = waypointsJoined.replaceFirst("[|]", "");

        String optimize = "true";
        String mode = "walking";

        String requestUrl = GOOGLE_API_URL_BASE +
                "/directions/json?" +
                "origin=" + origin +
                "&destination=" + destination +
                "&optimize=" + optimize +
                "&mode=" + mode +
                "&waypoints=" + waypoints +
                "&key=" + GOOGLE_MAPS_API_KEY;

        return requestUrl;
    }

    public String exportMapsUrlBuilder(String mapsRequestApiUrl){
        String exportMapsUrl = mapsRequestApiUrl.replace(GOOGLE_API_URL_BASE + "/directions/json?", GOOGLE_MAPS_EXPORT_URL_BASE)
               .replace("&optimize=true&mode=walking", "")
                .split("&key=")[0]
                .concat("&travelmode=walking");
        return exportMapsUrl;
    }

    public String addCityAndCountryDetails(String point, UserRequestDTO requestDTO, Boolean isWaypoint) {
        if (isWaypoint == false) {
            String pointWithSpaces = point + "%2C%20" + requestDTO.city() + "%2C%20" + requestDTO.country();
            return pointWithSpaces.replaceAll("[\s']", "%20");
        }
        String pointWithSpaces = "%7Cvia%3A" + point + "%2C" + requestDTO.city() + "%2C" + requestDTO.country();
        return pointWithSpaces.replaceAll("[\s']", "%20");
   }

    public List<String> getPlaceImageUrl(String placeId){
        String requestUrl = String.format("%s/place/details/json" +
                "?fields=photos" +
                "&place_id=%s" +
                "&key=%s",GOOGLE_API_URL_BASE,placeId, GOOGLE_MAPS_API_KEY) ;
        System.out.println(requestUrl);
        GMapResponseDTO responseDTO = restTemplate.getForObject(requestUrl, GMapResponseDTO.class);
        if (responseDTO.result().photos() == null) {
            return new ArrayList<>();
        }
        return responseDTO.result().photos()
                .stream()
                .limit(5)
                .map(photo -> String.format("%s/place/photo" +
                        "?maxwidth=%d" +
                        "&photo_reference=%s" +
                        "&key=%s",GOOGLE_API_URL_BASE, imageMaxWidth, photo.photoReference(), GOOGLE_MAPS_API_KEY)).toList();
    }
}
