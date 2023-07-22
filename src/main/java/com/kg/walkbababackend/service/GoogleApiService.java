package com.kg.walkbababackend.service;


import com.kg.walkbababackend.model.openai.DTO.*;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import com.kg.walkbababackend.model.openai.DTO.directionsApi.DirectionsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final static String GOOGLE_MAPS_API_KEY = "AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw";

    private final static String GOOGLE_API_URL_BASE = "https://maps.googleapis.com/maps/api/directions/json?";

    public List<DirectionsResponseDTO> getRoutesToRender(List<OpenAIRouteDTO> routes, UserRequestDTO requestDTO) {
        return routes.stream()
                .map(route -> getOneRoute(route, requestDTO))
                .filter(this::checkIfRouteIsValid)
                .toList();

//        OpenAIResponseDTO response = restTemplate.getForObject(requestUrl, OpenAIResponseDTO.class);
    }

    public boolean checkIfRouteIsValid(DirectionsResponseDTO directionsResponseDTO) {
        return true;
    }

    public DirectionsResponseDTO getOneRoute(OpenAIRouteDTO routeDTO, UserRequestDTO requestDTO) {
        String requestUrl = directionApiUrlRequestBuilder(routeDTO, requestDTO);
        System.out.println("String:" + requestUrl);

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
                "origin=" + origin +
                "&destination=" + destination +
                "&optimize=" + optimize +
                "&mode=" + mode +
                "&waypoints=" + waypoints +
                "&key=" + GOOGLE_MAPS_API_KEY;
        System.out.println(requestUrl);
        return requestUrl;
    }

    public String addCityAndCountryDetails(String point, UserRequestDTO requestDTO, Boolean isWaypoint) {
        if (isWaypoint == false) {
            String pointWithSpaces = point + "%2C%20" + requestDTO.city() + "%2C%20" + requestDTO.country();
            return pointWithSpaces.replaceAll("[\s']", "%20");
        }
        String pointWithSpaces = "%7Cvia%3A" + point + "%2C" + requestDTO.city() + "%2C" + requestDTO.country();
        return pointWithSpaces.replaceAll("[\s']", "%20");

//        if (isWaypoint == false) {
//            return point + ", " + requestDTO.city() + ", " + requestDTO.country();
//        }
//        return "|via:" + point + " " + requestDTO.city() + " " + requestDTO.country();
   }

}
