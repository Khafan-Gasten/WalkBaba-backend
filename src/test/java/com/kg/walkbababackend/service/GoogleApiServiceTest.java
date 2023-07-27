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

    String tehran_Gpt_Response = """
            [
                {
                  "walk_name": "Nature Walk",
                  "description": "Explore the natural beauty of Tehran",
                  "waypoints": [
                    {
                      "waypoint_name": "Darakeh",
                      "description": "A popular hiking spot at the foot of the Alborz Mountains"
                    },
                    {
                      "waypoint_name": "Jamshidieh Park",
                      "description": "A tranquil park known for its beautiful gardens and waterfalls"
                    },
                    {
                      "waypoint_name": "Pardisan Park",
                      "description": "One of the largest parks in Tehran, perfect for a leisurely walk"
                    }
                  ],
                  "theme": "Nature"
                },
                {
                  "walk_name": "Architectural Delights",
                  "description": "Discover Tehran's architectural masterpieces",
                  "waypoints": [
                    {
                      "waypoint_name": "Golestan Palace",
                      "description": "A UNESCO World Heritage Site, showcasing exquisite Persian architecture"
                    },
                    {
                      "waypoint_name": "Azadi Tower",
                      "description": "An iconic symbol of Tehran, offering panoramic city views"
                    },
                    {
                      "waypoint_name": "Milad Tower",
                      "description": "One of the world's tallest towers, boasting modern design and a revolving restaurant"
                    }
                  ],
                  "theme": "Architecture"
                },
                {
                  "walk_name": "Tehran Food Trail",
                  "description": "Indulge in Tehran's culinary delights",
                  "waypoints": [
                    {
                      "waypoint_name": "Tajrish Bazaar",
                      "description": "A bustling market offering a variety of fresh produce, spices, and local street food"
                    },
                    {
                      "waypoint_name": "Darband",
                      "description": "A vibrant area with numerous traditional restaurants serving delicious Persian cuisine"
                    },
                    {
                      "waypoint_name": "Reyhan Restaurant",
                      "description": "A popular eatery known for its authentic Iranian dishes and cozy ambiance"
                    }
                  ],
                  "theme": "Food"
                },
                {
                  "walk_name": "Cultural Exploration",
                  "description": "Immerse yourself in Tehran's rich culture",
                  "waypoints": [
                    {
                      "waypoint_name": "National Museum of Iran",
                      "description": "Discover Iran's history through a vast collection of artifacts and artworks"
                    },
                    {
                      "waypoint_name": "Tehran Grand Bazaar",
                      "description": "An ancient market offering traditional crafts, spices, carpets, and more"
                    },
                    {
                      "waypoint_name": "Niavaran Cultural Complex",
                      "description": "A cultural hub featuring museums, galleries, and beautiful gardens"
                    }
                  ],
                  "theme": "Culture"
                },
                {
                  "walk_name": "Tehran Highlights",
                  "description": "Experience the top attractions of Tehran",
                  "waypoints": [
                    {
                      "waypoint_name": "Sa'dabad Complex",
                      "description": "Former royal summer residence with magnificent palaces and museums"
                    },
                    {
                      "waypoint_name": "The Treasury of National Jewels",
                      "description": "An opulent collection of Persian jewelry, including the stunning Peacock Throne"
                    },
                    {
                      "waypoint_name": "Tehran Contemporary Art Museum",
                      "description": "A must-visit for art enthusiasts, showcasing modern Iranian artworks"
                    }
                  ],
                  "theme": "Top Highlights"
                },
                {
                  "walk_name": "Family Fun Walk",
                  "description": "Enjoy Tehran with the whole family",
                  "waypoints": [
                    {
                      "waypoint_name": "Mellat Park",
                      "description": "A vast park offering playgrounds, paddle boats, and picnic areas"
                    },
                    {
                      "waypoint_name": "Tehran Birds Garden",
                      "description": "A bird-themed park with aviaries, a lake, and educational activities"
                    },
                    {
                      "waypoint_name": "Museum of Miniature and Cinema",
                      "description": "Fascinating museum displaying miniature models and movie props"
                    }
                  ],
                  "theme": "Family fun"
                }
              ]
            """;

    String cologne_Gpt_Response = """
            {
              "walks": [
                {
                  "walk_name": "Rhein River Promenade",
                  "description": "Enjoy a leisurely walk along the iconic Rhein River and take in the stunning views of the city.",
                  "waypoints": [
                    {
                      "waypoint_name": "Hohenzollern Bridge",
                      "description": "One of Cologne's most famous landmarks, offering panoramic views of the river and the Cologne Cathedral."
                    },
                    {
                      "waypoint_name": "Rheingarten",
                      "description": "A beautiful riverside park with colorful flower beds, relaxing benches, and a peaceful atmosphere."
                    },
                    {
                      "waypoint_name": "Tanzbrunnen",
                      "description": "An open-air concert venue surrounded by lush greenery and located right on the riverbank."
                    }
                  ],
                  "theme": "Nature"
                },
                {
                  "walk_name": "Architectural Marvels",
                  "description": "Explore Cologne's remarkable architecture, spanning from ancient Roman structures to modern masterpieces.",
                  "waypoints": [
                    {
                      "waypoint_name": "Cologne Cathedral",
                      "description": "A UNESCO World Heritage Site and Germany's most visited landmark, boasting incredible Gothic architecture and stunning stained glass windows."
                    },
                    {
                      "waypoint_name": "Cologne City Hall",
                      "description": "A magnificent example of historic architecture, showcasing an impressive Renaissance facade and beautiful interior decorations."
                    },
                    {
                      "waypoint_name": "Lanxess Arena",
                      "description": "One of the largest and most modern multi-purpose arenas in Europe, hosting various events and concerts throughout the year."
                    }
                  ],
                  "theme": "Architecture"
                },
                {
                  "walk_name": "Culinary Delights",
                  "description": "Embark on a gastronomic journey through the vibrant food scene of Cologne, treating your taste buds to delicious local specialties.",
                  "waypoints": [
                    {
                      "waypoint_name": "Elsässer Straße",
                      "description": "A lively street filled with traditional beer gardens, cozy cafes, and eateries serving mouthwatering German dishes."
                    },
                    {
                      "waypoint_name": "Cologne Chocolate Museum",
                      "description": "Discover the fascinating world of chocolate, learn about its history, and indulge in sweet treats at the museum's cafe."
                    },
                    {
                      "waypoint_name": "Brauhaus Früh am Dom",
                      "description": "A famous brewery and restaurant offering traditional Cologne cuisine. Don't miss trying the local Kölsch beer."
                    }
                  ],
                  "theme": "Food"
                },
                {
                  "walk_name": "Cultural Treasures",
                  "description": "Immerse yourself in Cologne's rich cultural heritage and explore its museums, galleries, and historical attractions.",
                  "waypoints": [
                    {
                      "waypoint_name": "Museum Ludwig",
                      "description": "A renowned museum featuring an impressive collection of modern art, including works by Picasso, Warhol, and Lichtenstein."
                    },
                    {
                      "waypoint_name": "Romano-Germanic Museum",
                      "description": "Discover ancient Roman artifacts, including the famous Dionysus mosaic, in this archaeological museum."
                    },
                    {
                      "waypoint_name": "Hänneschen-Theater",
                      "description": "Enjoy a traditional puppet theater show featuring beloved characters and humorous stories from Cologne's folklore."
                    }
                  ],
                  "theme": "Culture"
                },
                {
                  "walk_name": "Top Cologne Highlights",
                  "description": "Experience the must-see sights of Cologne, covering its iconic landmarks and most beloved attractions.",
                  "waypoints": [
                    {
                      "waypoint_name": "Kölner Dom",
                      "description": "The Cologne Cathedral, a masterpiece of Gothic architecture and the symbol of the city."
                    },
                    {
                      "waypoint_name": "Cologne Old Town",
                      "description": "Wander through the charming narrow streets, admire colorful historic buildings, and visit quaint shops and cafes."
                    },
                    {
                      "waypoint_name": "Hohenzollern Bridge",
                      "description": "Capture breathtaking views of the cathedral and the city skyline from this famous bridge."
                    }
                  ],
                  "theme": "Top Highlights"
                },
                {
                  "walk_name": "Family Fun in Cologne",
                  "description": "Enjoy a fun-filled day with your family, exploring attractions and activities suitable for all ages.",
                  "waypoints": [
                    {
                      "waypoint_name": "Cologne Zoo",
                      "description": "Home to over 10,000 animals, including elephants, giraffes, and big cats. Don't miss the aquarium and monkey house."
                    },
                    {
                      "waypoint_name": "Odysseum",
                      "description": "A science adventure museum offering interactive exhibits, educational displays, and hands-on experiments for children."
                    },
                    {
                      "waypoint_name": "Rheinpark",
                      "description": "A large park with playgrounds, picnic areas, and mini-golf, perfect for a family outdoor activity or a relaxing stroll."
                    }
                  ],
                  "theme": "Family Fun"
                }
              ]
            }
           
            """;
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

    private static final String EDINBURGH_RESPONSE_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=Edinburgh%20Castle%2C%20Edinburgh%2C%20UK&destination=Palace%20of%20Holyroodhouse%2C%20Edinburgh%2C%20UK&mode=walking&waypoints=optimize%3Atrue%7Cvia%3ARoyal%20Mile%2CEdinburgh%2CUK%7Cvia%3ASt.%20Giles%20%20Cathedral%2CEdinburgh%2CUK&key=AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw";
    private static final String EDINBURGH_RESPONSE_URL_STOPOVERS = "https://maps.googleapis.com/maps/api/directions/json?origin=Edinburgh%20Castle%2C%20Edinburgh%2C%20UK&destination=Palace%20of%20Holyroodhouse%2C%20Edinburgh%2C%20UK&mode=walking&waypoints=optimize%3Atrue%7CRoyal%20Mile%2CEdinburgh%2CUK%7CSt.%20Giles%20%20Cathedral%2CEdinburgh%2CUK&key=AIzaSyBmOpstO2144GQzwOWrWL9NQLvQ5oyE_kw";

    private static final String EDINBURGH_EXPORT_MAP_RESPONSE_URL = "https://www.google.com/maps/dir/?api=1&origin=Edinburgh%20Castle%2C%20Edinburgh%2C%20UK&destination=Palace%20of%20Holyroodhouse%2C%20Edinburgh%2C%20UK&waypoints=%7Cvia%3ARoyal%20Mile%2CEdinburgh%2CUK%7Cvia%3ASt.%20Giles%20%20Cathedral%2CEdinburgh%2CUK&travelmode=walking";
    @Test
    public void urlBuilderReturnsCorrectUrlFormat() {
        String url = googleApiService.directionApiUrlRequestBuilder(route, requestDTO, false);
        System.out.println(url);
        assert(url.length() > 0);
        assertEquals(EDINBURGH_RESPONSE_URL, url);
    }

    @Test
    public void urlBuilderStopoversReturnsCorrectUrlFormat() {
        String requestUrl = googleApiService.directionApiUrlRequestBuilder(route, requestDTO, false);
        String url = googleApiService.directionApiUrlRequestBuilderStopovers(requestUrl);
        System.out.println(url);
        assert(url.length() > 0);
        assertEquals(EDINBURGH_RESPONSE_URL_STOPOVERS, url);
    }

    @Test
    public void urlBuilderReversesWaypoints() {
        String url = googleApiService.directionApiUrlRequestBuilder(route, requestDTO, true);
        System.out.println(url);
        assert(url.length() > 0);
        assertNotEquals(EDINBURGH_RESPONSE_URL, url);
    }

    @Test
    public void exportUrlBuilderReturnsCorrectUrl() {
        String url = googleApiService.exportMapsUrlBuilder(EDINBURGH_RESPONSE_URL);
        System.out.println(url);
        assert(url.length() > 0);
        assertEquals(EDINBURGH_EXPORT_MAP_RESPONSE_URL, url);
    }

    @Test
    public void routeFetcherReturnsARoute() {
        DirectionsResponseDTO directionsResponseDTO = googleApiService.callDirectionsApi(EDINBURGH_RESPONSE_URL);
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
    public void getOneRouteShouldReturnCorrectDTORotterdam(){
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
    public void getOneRouteShouldReturnCorrectDTOCologne(){
        UserRequestDTO cologneRequestDTO = new UserRequestDTO("Germany", "Cologne", "1", null, null);
        List<OpenAIRouteDTO> openAIRouteDTOS = openAIService.getListOfRoute(cologne_Gpt_Response);
        OpenAIRouteDTO route1 = openAIRouteDTOS.get(0);
        System.out.println(route1.name());
        System.out.println(route1.theme());
        RouteToFrontEndDTO routeToFrontEndDTO = googleApiService.getOneRoute(route1, cologneRequestDTO);
        assertNotNull(routeToFrontEndDTO);
        assertNotNull(routeToFrontEndDTO.distance());
        assertNotNull(routeToFrontEndDTO.durationInMin());
        assertEquals("Rhein River Promenade", routeToFrontEndDTO.name());
        assertEquals("Germany", routeToFrontEndDTO.country());
        assertEquals("Cologne", routeToFrontEndDTO.city());
        assertEquals("Enjoy a leisurely walk along the iconic Rhein River and take in the stunning views of the city.", routeToFrontEndDTO.description());
        assertNotNull(routeToFrontEndDTO.waypoints());
        assert(routeToFrontEndDTO.waypoints().get(0).imageLink().get(0).length()>0);
    }

    @Test
    public void getRoutesToRenderShouldReturnCorrectDTOSRotterdam(){
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

//    @Test
//    public void getRoutesToRenderShouldReturnCorrectDTOSCologne(){
//        UserRequestDTO cologneRequestDTO = new UserRequestDTO("Germany", "Cologne", "1", null, null);
//        List<OpenAIRouteDTO> openAIRouteDTOS = openAIService.getListOfRoute(cologne_Gpt_Response);
//        List<RouteToFrontEndDTO> routeToFrontEndDTOS = googleApiService.getRoutesToRender(openAIRouteDTOS, cologneRequestDTO);
//        assertNotNull(routeToFrontEndDTOS);
//        assertNotNull(routeToFrontEndDTOS.get(1).distance());
//        assertNotNull(routeToFrontEndDTOS.get(2).durationInMin());
//        assertEquals("Culinary Delights", routeToFrontEndDTOS.get(1).name());
//        assertEquals("Germany", routeToFrontEndDTOS.get(2).country());
//        assertEquals("Cologne", routeToFrontEndDTOS.get(0).city());
//        assertEquals("Enjoy a leisurely walk along the iconic Rhein River and take in the stunning views of the city.", routeToFrontEndDTOS.get(0).description());
//        assertNotNull(routeToFrontEndDTOS.get(2).waypoints());
//        assert(routeToFrontEndDTOS.get(1).waypoints().get(0).imageLink().get(0).length()>0);
//    }

//    @Test
//    public void getRoutesToRenderShouldReturnCorrectDTOSTehran(){
//        UserRequestDTO tehranRequestDTO = new UserRequestDTO("Iran", "Tehran", "1", null, null);
//        List<OpenAIRouteDTO> openAIRouteDTOS = openAIService.getListOfRoute(tehran_Gpt_Response);
//        List<RouteToFrontEndDTO> routeToFrontEndDTOS = googleApiService.getRoutesToRender(openAIRouteDTOS, tehranRequestDTO);
//        assertNotNull(routeToFrontEndDTOS);
//        assertNotNull(routeToFrontEndDTOS.get(1).distance());
//        assertNotNull(routeToFrontEndDTOS.get(2).durationInMin());
//        System.out.println(routeToFrontEndDTOS.get(1).name());
//        System.out.println(routeToFrontEndDTOS.get(2).country());
//        System.out.println(routeToFrontEndDTOS.get(0).city());
//        System.out.println(routeToFrontEndDTOS.get(3).description());
//        assertNotNull(routeToFrontEndDTOS.get(1).waypoints());
////        assert(routeToFrontEndDTOS.get(1).waypoints().get(0).imageLink().get(0).length()>0);
//    }
}
