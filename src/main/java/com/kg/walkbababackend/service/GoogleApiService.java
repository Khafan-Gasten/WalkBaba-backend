package com.kg.walkbababackend.service;


import com.kg.walkbababackend.model.openai.DTO.OpenAIResponseDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.WaypointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleApiService {

    @Autowired
    RestTemplate restTemplate;

    private final static String GOOGLE_MAPS_API_KEY = "AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw";

    private final static String GOOGLE_API_URL_BASE = "https://maps.googleapis.com/maps/api/directions/json?";

    public String getRoutesToRender(OpenAIRouteDTO routes, UserRequestDTO requestDTO) {

        List<WaypointDTO> waypointsDTOList = routes.waypoints();

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
        String waypoints = waypointsJoined.replaceFirst("%7C", "");

        String optimize = "true";

        String mode = "walking";

        String requestUrl = GOOGLE_API_URL_BASE +
                "origin=" + origin +
                "&destination=" + destination +
                "&optimize=" + optimize +
                "&mode=" + mode +
                "&waypoints=" + waypoints +
                "&key=" + GOOGLE_MAPS_API_KEY;

        return requestUrl;

//        OpenAIResponseDTO response = restTemplate.getForObject(requestUrl, OpenAIResponseDTO.class);
    }

    public String addCityAndCountryDetails(String point, UserRequestDTO requestDTO, Boolean isWaypoint) {
        if (isWaypoint == false) {
            String pointWithSpaces = point + "%2C%20" + requestDTO.city() + "%2C%20" + requestDTO.country();
            return pointWithSpaces.replaceAll("[\s']", "%20");
        }
        String pointWithSpaces = "%7Cvia%3A" + point + "%2C" + requestDTO.city() + "%2C" + requestDTO.country();
        return pointWithSpaces.replaceAll("[\s']", "%20");
    }
}
