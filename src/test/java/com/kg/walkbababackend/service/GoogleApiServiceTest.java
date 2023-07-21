package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.WaypointDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoogleApiServiceTest {


    GoogleApiService googleApiService = new GoogleApiService();

    WaypointDTO waypoint1 = new WaypointDTO("Edinburgh Castle", "Castle perched atop an ancient volcanic rock.");
    WaypointDTO waypoint2 = new WaypointDTO("Royal Mile", "Famous street connecting the castle and palace.");
    WaypointDTO waypoint3 = new WaypointDTO("St. Giles' Cathedral", "Impressive Gothic cathedral on the Royal Mile.");
    WaypointDTO waypoint4 = new WaypointDTO("Palace of Holyroodhouse", "Official residence of the British monarch in Scotland.");

    List<WaypointDTO> waypoints = new ArrayList<>(Arrays.asList(waypoint1, waypoint2, waypoint3, waypoint4));
    OpenAIRouteDTO route = new OpenAIRouteDTO("Old Town Exploration", "Discover the historic charm of Edinburgh's Old Town.", waypoints);

    UserRequestDTO requestDTO = new UserRequestDTO("UK", "Edinburgh", "1", "Food", false);

    @Test
    public void returnsCorrectUrlFormat() {
        String url = googleApiService.getRoutesToRender(route, requestDTO);
        System.out.println(url);
        assert(url.length() > 0);
    }


}
