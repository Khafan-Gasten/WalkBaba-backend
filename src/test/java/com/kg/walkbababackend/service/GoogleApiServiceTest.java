package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import com.kg.walkbababackend.model.openai.DTO.directionsApi.DirectionsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GoogleApiServiceTest {



    @Autowired
    GoogleApiService googleApiService;

    WaypointDTO waypoint1 = new WaypointDTO("Edinburgh Castle", "Castle perched atop an ancient volcanic rock.");
    WaypointDTO waypoint2 = new WaypointDTO("Royal Mile", "Famous street connecting the castle and palace.");
    WaypointDTO waypoint3 = new WaypointDTO("St. Giles' Cathedral", "Impressive Gothic cathedral on the Royal Mile.");
    WaypointDTO waypoint4 = new WaypointDTO("Palace of Holyroodhouse", "Official residence of the British monarch in Scotland.");

    List<WaypointDTO> waypoints = new ArrayList<>(Arrays.asList(waypoint1, waypoint2, waypoint3, waypoint4));
    OpenAIRouteDTO route = new OpenAIRouteDTO("Old Town Exploration", "Discover the historic charm of Edinburgh's Old Town.", waypoints);

    UserRequestDTO requestDTO = new UserRequestDTO("UK", "Edinburgh", "1", "Food", false);

    private static final String EDINBURGH_RESPONSE_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=Edinburgh%20Castle%2C%20Edinburgh%2C%20UK&destination=Palace%20of%20Holyroodhouse%2C%20Edinburgh%2C%20UK&optimize=true&mode=walking&waypoints=via%3ARoyal%20Mile%2CEdinburgh%2CUK%7Cvia%3ASt.%20Giles%20%20Cathedral%2CEdinburgh%2CUK&key=AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw";
    @Test
    public void urlBuilderReturnsCorrectUrlFormat() {
        String url = googleApiService.directionApiUrlRequestBuilder(route, requestDTO);
        System.out.println(url);
        assert(url.length() > 0);
        assertEquals(EDINBURGH_RESPONSE_URL, url);
    }

    @Test
    public void routeFetcherReturnsARoute() {
        DirectionsResponseDTO directionsResponseDTO = googleApiService.getOneRoute(route, requestDTO);
        System.out.println(directionsResponseDTO);
        System.out.println(directionsResponseDTO.geocodedWaypointList());
        assertNotNull(directionsResponseDTO);
    }

    @Test
    public void getPlaceImageUrlShouldReturnCorrectUrl(){
        List<String> actual = googleApiService.getPlaceImageUrl("ChIJN1t_tDeuEmsRUsoyG83frY4");
        assertNotNull(actual);
        assertTrue(actual.size() > 0);
    }
}
