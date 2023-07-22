package com.kg.walkbababackend.service;


import com.kg.walkbababackend.model.openai.DTO.*;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import com.kg.walkbababackend.model.openai.DTO.directionsApi.DirectionsResponseDTO;
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

    @Value("${googleMap.imageMaxWidth}")
    Long imageMaxWidth;

    int SHORTEST_WALK_TIME_S = 1200;
    int LONGEST_WALK_TIME_S = 18000;
    int SHORTEST_WALK_DIST_M = 1000;
    int LONGEST_WALK_DIST_M = 12000;

    public List<DirectionsResponseDTO> getRoutesToRender(List<OpenAIRouteDTO> routes, UserRequestDTO requestDTO) {
        return routes.stream()
                .map(route -> getOneRoute(route, requestDTO))
                .filter(this::checkIfRouteIsValid)
                .toList();
    }

    public boolean checkIfRouteIsValid(DirectionsResponseDTO directionsResponseDTO) {
        int totalDistance = directionsResponseDTO.routes().get(0).legs.stream()
                .mapToInt(leg -> leg.distance.value).sum();
        int totalDuration = directionsResponseDTO.routes().get(0).legs.stream()
                .mapToInt(leg -> leg.duration.value).sum();
        System.out.println("duration " + totalDuration + " distance " + totalDistance);
        return totalDuration <= LONGEST_WALK_TIME_S && totalDuration >= SHORTEST_WALK_TIME_S
                && totalDistance <= LONGEST_WALK_DIST_M && totalDistance >= SHORTEST_WALK_DIST_M;
    }

    public DirectionsResponseDTO getOneRoute(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO) {
        String requestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO);
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
        GMapResponseDTO responseDTO = restTemplate.getForObject(requestUrl, GMapResponseDTO.class);
        return responseDTO.result().photos()
                .stream()
                .limit(5)
                .map(photo -> String.format("%s/place/photo" +
                        "?maxwidth=%d" +
                        "&photo_reference=%s" +
                        "&key=%s",GOOGLE_API_URL_BASE,  imageMaxWidth, photo.photoReference(), GOOGLE_MAPS_API_KEY)).toList();

    }
}
