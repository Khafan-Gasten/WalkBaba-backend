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

    Long SHORTEST_WALK_TIME_MIN = 10L;
    Long LONGEST_WALK_TIME_MIN = 300L;

    public List<RouteToFrontEndDTO> getRoutesToRender(List<OpenAIRouteDTO> routes, UserRequestDTO requestDTO) {
        return routes.stream()
                .map(route -> getOneRoute(route, requestDTO))
                .filter(route -> checkIfRouteIsValid(route.durationInMin()))
                .toList();
    }

    public boolean checkIfRouteIsValid(Long duration) {
        return duration <= LONGEST_WALK_TIME_MIN && duration >= SHORTEST_WALK_TIME_MIN;
    }

    public RouteToFrontEndDTO getOneRoute(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO) {
        String requestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO, false);
        String reverseRequestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO, true);


        DirectionsResponseDTO directions = callDirectionsApi(directionApiUrlRequestBuilderStopovers(requestUrl));
        ExportLinkDTO exportLinks;

        Double totalDist;
        Long totalDur;
        if (directions.routes().isEmpty()) {
            totalDist = 0.0;
            totalDur = 0L;
            exportLinks = new ExportLinkDTO("", "", "");
        } else {
            System.out.println("WAYPOINTS REARANGED: " + directions.routes().get(0).waypoint_order.toString());
            totalDist = calculateDistance(directions.routes().get(0).legs);
            totalDur = calculateDuration(directions.routes().get(0).legs);
            exportLinks = urlBuilders(requestUrl, reverseRequestUrl, directions.routes().get(0).waypoint_order);

        }
        List<List<String>> imageUrls = directions.geocodedWaypointList().stream()
                .map(waypoint -> getPlaceImageUrl(waypoint.place_id))
                .toList();
        List<WaypointDTO> newWaypointDTOS = new ArrayList<>();
        List<WaypointDTO> reJigWaypoints = rearrangeWaypoints(routeDTO.waypoints(), directions.routes().get(0).waypoint_order);

        for (int i = 0; i < reJigWaypoints.size(); i++) {
            newWaypointDTOS.add(reJigWaypoints.get(i).withImageLink(imageUrls.get(i)));
        }
//        List<WaypointDTO> rearrangedWaypointDTOS = rearrangeWaypoints(newWaypointDTOS, directions.routes().get(0).waypoint_order);
        return new RouteToFrontEndDTO(routeDTO, requestDTO, totalDist, totalDur, exportLinks, newWaypointDTOS);
    }

    private List<WaypointDTO> rearrangeWaypoints(List<WaypointDTO> newWaypointDTOS, ArrayList<Object> waypointOrder) {
        List<WaypointDTO> rearrangedWaypointDTOS = new ArrayList<>();
        rearrangedWaypointDTOS.add(newWaypointDTOS.get(0));
        for (int i=0; i < waypointOrder.size(); i++) {
            rearrangedWaypointDTOS.add(newWaypointDTOS.get(1+((Integer) waypointOrder.get(i))));
        }
        rearrangedWaypointDTOS.add(newWaypointDTOS.get(newWaypointDTOS.size()-1));
        return rearrangedWaypointDTOS;
    }

    private String startExportMapsUrlBuilder(String exportLink) {
        String startingPointUrl = exportLink.split("&origin=")[1].split("&destination=")[0];
        return exportLink.replace("&origin=" + startingPointUrl,"")
                .replace("&waypoints=", ("&waypoints=" + startingPointUrl));
    }

    public double calculateDistance(List<Leg> legs) {
        return ((double) Math.round((((legs.stream().mapToDouble(leg -> leg.distance.value).sum())))/100))/10;
    }

    public long calculateDuration(List<Leg> legs) {
        return ((legs.stream().mapToInt(leg -> leg.distance.value).sum()))/60;
    }

    public DirectionsResponseDTO callDirectionsApi(String requestUrl) {
        URI builtURI = URI.create(requestUrl);
        System.out.println("URI: " + builtURI);
        DirectionsResponseDTO response = restTemplate.getForObject(builtURI, DirectionsResponseDTO.class);
        return response;
    }

    public ExportLinkDTO urlBuilders(String requestUrl, String reverseRequestUrl, ArrayList<Object> waypointOrder) {
        String[] reJiggedUrl = rearrangeUrl(requestUrl, waypointOrder);
        String exportLink = exportMapsUrlBuilder(reJiggedUrl[0]);
        System.out.println(exportLink);
        String flippedWaypointsUrl = exportMapsUrlBuilder(reJiggedUrl[1]);

        String startExportLink = startExportMapsUrlBuilder(exportLink) ;
        String endExportLink = startExportMapsUrlBuilder(flippedWaypointsUrl);

        return new ExportLinkDTO(exportLink, startExportLink, endExportLink);
    }

    public String[] rearrangeUrl(String requestUrl, ArrayList<Object> waypointOrder) {
        System.out.println(requestUrl);
        String urlBase = requestUrl.split("origin=")[0]+ "origin=";
        String originalStartingPoint = requestUrl.split("origin=")[1].split("&destination=")[0];
        String originalDestPoint = requestUrl.split("&destination=")[1].split("&mode=")[0];
        String[] originalWaypoints = requestUrl.split("&waypoints=optimize%3Atrue%7Cvia%3A")[1].split("&key")[0].split("%7Cvia%3A");
        List<Integer> waypointOrderInt = waypointOrder.stream().map(object -> (Integer) object).toList();
        String toStartWaypointsString = "";
        for (int i=0; i < originalWaypoints.length; i++) {
            toStartWaypointsString += "%7Cvia%3A" + originalWaypoints[waypointOrderInt.get(i)];
        }
        String toEndWaypointsString = "";
        for (int i=0; i < originalWaypoints.length; i++) {
            toEndWaypointsString = "%7Cvia%3A" + originalWaypoints[waypointOrderInt.get(i)] +toEndWaypointsString;
        }
        String toStartPointRearranged =
                urlBase +
                originalStartingPoint + "&destination=" +
                originalDestPoint + "&mode=walking&waypoints=optimize%3Atrue" +
                toStartWaypointsString +
                        "&key=" + requestUrl.split("&key")[1];
        String toEndPointRearranged =
                urlBase +
                        originalDestPoint + "&destination=" +
                        originalStartingPoint + "&mode=walking&waypoints=optimize%3Atrue" +
                        toEndWaypointsString +
                        "&key=" + requestUrl.split("&key")[1];
        System.out.println(toStartPointRearranged);
        return new String[]{toStartPointRearranged, toEndPointRearranged};

    }
   // https://maps.googleapis.com/maps/api/directions/json?origin=Edinburgh%20Castle%2C%20Edinburgh%2C%20UK&destination=Palace%20of%20Holyroodhouse%2C%20Edinburgh%2C%20UK&mode=walking&waypoints=optimize%3Atrue%7Cvia%3ARoyal%20Mile%2CEdinburgh%2CUK%7Cvia%3ASt.%20Giles%20%20Cathedral%2CEdinburgh%2CUK&key=AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw

    public String directionApiUrlRequestBuilder(OpenAIRouteDTO route, UserRequestDTO requestDTO, Boolean reverseWaypoints) {
        List<WaypointDTO> waypointsDTOList = route.waypoints();
        List<WaypointDTO> waypointDTOListToUse;

        if (reverseWaypoints == true){
            List<WaypointDTO> reversedDTOList = new ArrayList<>(waypointsDTOList);
            Collections.reverse(reversedDTOList);
            waypointDTOListToUse = reversedDTOList;
        } else {
            waypointDTOListToUse = waypointsDTOList;
        }

        List<String> waypointsList = new ArrayList<>();
        for (int i = 0; i < waypointDTOListToUse.size(); i++) {
            if (i == 0 || i == waypointDTOListToUse.size()-1) {
                waypointsList.add(addCityAndCountryDetails(waypointDTOListToUse.get(i).name(), requestDTO, false));
                continue;
            }
            waypointsList.add(addCityAndCountryDetails(waypointDTOListToUse.get(i).name(), requestDTO, true));
        }

        String origin = waypointsList.remove(0);
        String destination = waypointsList.remove(waypointsList.size()-1);

        String waypointsJoined = String.join("", waypointsList);
        String waypoints = waypointsJoined.replaceFirst("[|]", "");

        String mode = "walking";

        String requestUrl = GOOGLE_API_URL_BASE +
                "/directions/json?" +
                "origin=" + origin +
                "&destination=" + destination +
                "&mode=" + mode +
                "&waypoints=optimize%3Atrue" + waypoints +
                "&key=" + GOOGLE_MAPS_API_KEY;

        return requestUrl;
    }

    public String directionApiUrlRequestBuilderStopovers(String requestUrl) {
        return requestUrl.replaceAll("via%3A", "");
    }

    public String exportMapsUrlBuilder(String mapsRequestApiUrl){
        String exportMapsUrl = mapsRequestApiUrl.replace(GOOGLE_API_URL_BASE + "/directions/json?", GOOGLE_MAPS_EXPORT_URL_BASE)
               .replace("&mode=walking", "")
                .replace("optimize%3Atrue", "")
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
                .limit(7)
                .map(photo -> String.format("%s/place/photo" +
                        "?maxwidth=%s" +
                        "&photo_reference=%s" +
                        "&key=%s",GOOGLE_API_URL_BASE, imageMaxWidth, photo.photoReference(), GOOGLE_MAPS_API_KEY)).toList();
    }
}
