package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import com.kg.walkbababackend.model.openai.DTO.MapsApi.directionsApi.DirectionsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GoogleApiServiceTest {
    String rotterdam_Gpt_Response = """
    [
    {
        "walk_name": "Nature Walk in Rotterdam",
            "city": "Rotterdam",
            "country": "Netherlands",
            "description": "Explore the natural beauty of Rotterdam while visiting its top natural attractions.",
            "waypoints": [
        {
            "waypoint_name": "Kralingse Bos",
                "description": "A beautiful park with a lake, perfect for picnics and leisurely walks."
        },
        {
            "waypoint_name": "Het Park",
                "description": "A peaceful park offering stunning views of the city skyline and the Maas River."
        },
        {
            "waypoint_name": "Rotterdam Zoo",
                "description": "Discover a wide variety of animal species in this well-maintained zoo."
        }
  ],
        "theme": "Nature"
    }, {
        "walk_name": "Architectural Marvels in Rotterdam",
                "city": "Rotterdam",
                "country": "Netherlands",
                "description": "Marvel at the architectural wonders that define Rotterdam's skyline.",
                "waypoints": [
        {
            "waypoint_name": "Cube Houses",
                "description": "An innovative residential project featuring unique cube-shaped houses."
        },
        {
            "waypoint_name": "Erasmusbrug",
                "description": "A striking bridge connecting the northern and southern parts of Rotterdam."
        },
        {
            "waypoint_name": "Markthal",
                "description": "A spectacular market hall known for its colorful ceiling artwork."
        }
  ],
        "theme": "Architecture"
    }, {
        "walk_name": "Food Tour in Rotterdam",
                "city": "Rotterdam",
                "country": "Netherlands",
                "description": "Embark on a culinary journey through Rotterdam, tasting delicious local delicacies.",
                "waypoints": [
        {
            "waypoint_name": "Markthal",
                "description": "Explore a variety of food stalls offering fresh produce and international cuisines."
        },
        {
            "waypoint_name": "Fenix Food Factory",
                "description": "A vibrant warehouse-turned-market offering artisanal food and craft beers."
        },
        {
            "waypoint_name": "Dudok Patisserie",
                "description": "Indulge in delectable pastries and desserts at this renowned patisserie."
        }
  ],
        "theme": "Food"
    }, {
        "walk_name": "Cultural Exploration of Rotterdam",
                "city": "Rotterdam",
                "country": "Netherlands",
                "description": "Immerse yourself in Rotterdam's rich cultural heritage and vibrant arts scene.",
                "waypoints": [
        {
            "waypoint_name": "Museum Boijmans Van Beuningen",
                "description": "Admire an extensive collection of artworks from various eras and styles."
        },
        {
            "waypoint_name": "Witte de Withstraat",
                "description": "Stroll down this trendy street lined with art galleries, shops, and cafes."
        },
        {
            "waypoint_name": "Kunsthal Rotterdam",
                "description": "Experience rotating exhibitions showcasing contemporary art and culture."
        }
  ],
        "theme": "Culture"
    }, {
        "walk_name": "Highlights of Rotterdam",
                "city": "Rotterdam",
                "country": "Netherlands",
                "description": "Discover the must-see attractions that make Rotterdam truly special.",
                "waypoints": [
        {
            "waypoint_name": "Euromast",
                "description": "Enjoy panoramic views of Rotterdam from the iconic Euromast tower."
        },
        {
            "waypoint_name": "Erasmus Bridge",
                "description": "Marvel at the stunning architecture of this famous bridge."
        },
        {
            "waypoint_name": "Delfshaven",
                "description": "Visit the historic harbor area with picturesque canals and charming buildings."
        }
  ],
        "theme": "Top Highlights"
    }, {
        "walk_name": "Family Fun in Rotterdam",
                "city": "Rotterdam",
                "country": "Netherlands",
                "description": "Unleash the joy and excitement of Rotterdam with fun activities for the whole family.",
                "waypoints": [
        {
            "waypoint_name": "Miniworld Rotterdam",
                "description": "Discover a miniature world depicting various aspects of Rotterdam."
        },
        {
            "waypoint_name": "Splashtours",
                "description": "Enjoy an amphibious bus tour, exploring Rotterdam on land and water."
        },
        {
            "waypoint_name": "FutureLand",
                "description": "Learn about the impressive Maasvlakte 2 and its futuristic port developments."
        }
  ],
        "theme": "Family fun"
    }]
    """;




    @Autowired
    GoogleApiService googleApiService;
    @Autowired
    OpenAIService openAIService;

    WaypointDTO waypoint1 = new WaypointDTO("Edinburgh Castle", "Castle perched atop an ancient volcanic rock.", null);
    WaypointDTO waypoint2 = new WaypointDTO("Royal Mile", "Famous street connecting the castle and palace.", null);
    WaypointDTO waypoint3 = new WaypointDTO("St. Giles' Cathedral", "Impressive Gothic cathedral on the Royal Mile.", null);
    WaypointDTO waypoint4 = new WaypointDTO("Palace of Holyroodhouse", "Official residence of the British monarch in Scotland.", null);

    List<WaypointDTO> waypoints = new ArrayList<>(Arrays.asList(waypoint1, waypoint2, waypoint3, waypoint4));
    OpenAIRouteDTO route = new OpenAIRouteDTO("Old Town Exploration", "Discover the historic charm of Edinburgh's Old Town.", null, waypoints);

    UserRequestDTO requestDTO = new UserRequestDTO("UK", "Edinburgh", "1", "Food", false);

    private static final String EDINBURGH_RESPONSE_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=Edinburgh%20Castle%2C%20Edinburgh%2C%20UK&destination=Palace%20of%20Holyroodhouse%2C%20Edinburgh%2C%20UK&optimize=true&mode=walking&waypoints=%7Cvia%3ARoyal%20Mile%2CEdinburgh%2CUK%7Cvia%3ASt.%20Giles%20%20Cathedral%2CEdinburgh%2CUK&key=AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw";
    @Test
    public void urlBuilderReturnsCorrectUrlFormat() {
        String url = googleApiService.directionApiUrlRequestBuilder(route, requestDTO);
        System.out.println(url);
        assert(url.length() > 0);
        assertEquals(EDINBURGH_RESPONSE_URL, url);
    }

    @Test
    public void routeFetcherReturnsARoute() {
        DirectionsResponseDTO directionsResponseDTO = googleApiService.callDirectionsApi(route, requestDTO);
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

    @Test
    public void getOneRouteShouldReturnCorrectDTO(){
        UserRequestDTO rDamRequestDTO= new UserRequestDTO("Netherlands", "Rotterdam", "1", null, null);
        List<OpenAIRouteDTO> openAIRouteDTOS = openAIService.getListOfRoute(rotterdam_Gpt_Response);
        OpenAIRouteDTO route1 = openAIRouteDTOS.get(0);
        System.out.println(route1.name());
        System.out.println(route1.theme());
        RouteToFrontEndDTO routeToFrontEndDTO = googleApiService.getOneRoute(route1, rDamRequestDTO);
        assertNotNull(routeToFrontEndDTO);
        assertNotNull(routeToFrontEndDTO.distance());
        assertNotNull(routeToFrontEndDTO.durationInMin());
        assertEquals("Nature Walk in Rotterdam", routeToFrontEndDTO.name());
        assertEquals("Netherlands", routeToFrontEndDTO.country());
        assertEquals("Rotterdam", routeToFrontEndDTO.city());
        assertEquals("Explore the natural beauty of Rotterdam while visiting its top natural attractions.", routeToFrontEndDTO.description());
        assertNotNull(routeToFrontEndDTO.waypoints());
        assert(routeToFrontEndDTO.waypoints().get(0).imageLink().get(0).length()>0);
    }

    @Test
    public void getRoutesToRenderShouldReturnCorrectDTO(){
        UserRequestDTO rDamRequestDTO= new UserRequestDTO("Netherlands", "Rotterdam", "1", null, null);
        List<OpenAIRouteDTO> openAIRouteDTOS = openAIService.getListOfRoute(rotterdam_Gpt_Response);
        List<RouteToFrontEndDTO> routeToFrontEndDTOS = googleApiService.getRoutesToRender(openAIRouteDTOS, rDamRequestDTO);
        assertNotNull(routeToFrontEndDTOS);
        assertNotNull(routeToFrontEndDTOS.get(1).distance());
        assertNotNull(routeToFrontEndDTOS.get(2).durationInMin());
        assertEquals("Architectural Marvels in Rotterdam", routeToFrontEndDTOS.get(1).name());
        assertEquals("Netherlands", routeToFrontEndDTOS.get(2).country());
        assertEquals("Rotterdam", routeToFrontEndDTOS.get(3).city());
        assertEquals("Explore the natural beauty of Rotterdam while visiting its top natural attractions.", routeToFrontEndDTOS.get(0).description());
        assertNotNull(routeToFrontEndDTOS.get(2).waypoints());
        assert(routeToFrontEndDTOS.get(1).waypoints().get(0).imageLink().get(0).length()>0);
    }
}
