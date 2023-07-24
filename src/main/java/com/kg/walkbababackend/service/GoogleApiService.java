package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.*;
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

    int SHORTEST_WALK_TIME_MIN = 20;
    int LONGEST_WALK_TIME_MIN = 300;
    int SHORTEST_WALK_DIST_KM = 1;
    int LONGEST_WALK_DIST_KM = 12;

    public List<RouteToFrontEndDTO> getRoutesToRender(List<OpenAIRouteDTO> routes, UserRequestDTO requestDTO) {
        return routes.stream()
                .map(route -> getOneRoute(route, requestDTO))
                .filter(route -> checkIfRouteIsValid(route.distance(), route.durationInMin()))
                .toList();
    }

    public boolean checkIfRouteIsValid(Long distance, Long duration) {
        return duration <= LONGEST_WALK_TIME_MIN && duration >= SHORTEST_WALK_TIME_MIN
                && distance <= LONGEST_WALK_DIST_KM && distance >= SHORTEST_WALK_DIST_KM;
    }

    public RouteToFrontEndDTO getOneRoute(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO) {
        String requestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO);
        String exportLink = exportMapsUrlBuilder(requestUrl);
        DirectionsResponseDTO directions = callDirectionsApi(routeDTO, requestDTO, requestUrl);
        Long[] totalDistAndDur = calculateTotals(directions.routes().get(0).legs);     //Why is it get 0?
        List<List<String>> imageUrls = directions.geocodedWaypointList().stream()
                .map(waypoint -> getPlaceImageUrl(waypoint.place_id))
                .toList();
        List<WaypointDTO> newWaypointDTOS = new ArrayList<>();
        List<WaypointDTO> waypoints = routeDTO.waypoints();
        for (int i = 0; i < waypoints.size(); i++) {
            newWaypointDTOS.add(waypoints.get(i).withImageLink(imageUrls.get(i)));
        }
        return new RouteToFrontEndDTO(routeDTO, requestDTO, totalDistAndDur, exportLink, newWaypointDTOS);
    }

    public Long[] calculateTotals(List<Leg> legs) {
        Long totalDistance = (long) (legs.stream().mapToInt(leg -> leg.distance.value).sum())/1000; //Need to be careful with rounding
        Long totalDuration = (long) (legs.stream().mapToInt(leg -> leg.duration.value).sum())/60;
        System.out.println("duration " + totalDuration + " distance " + totalDistance);
        return new Long[]{totalDistance, totalDuration};
    }

    public DirectionsResponseDTO callDirectionsApi(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO, String requestUrl) {
//        String requestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO);
        URI builtURI = URI.create(requestUrl);
        System.out.println("URI: " + builtURI);
        DirectionsResponseDTO response = restTemplate.getForObject(builtURI, DirectionsResponseDTO.class);
        return response;
    }

    public String directionApiUrlRequestBuilder(OpenAIRouteDTO route, UserRequestDTO requestDTO) {
        List<WaypointDTO> waypointsDTOList = route.waypoints();
        List<String> waypointsList = new ArrayList<>();

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
               .replace("&mode=walking", "")
                .replace("&optimize=true", "")
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
