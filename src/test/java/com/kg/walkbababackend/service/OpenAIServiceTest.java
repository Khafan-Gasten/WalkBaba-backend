package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceTest {

    private static final String BRISTOL_CHAT_GPT_RESPONSE = """
        {
          "walking_tours": [
            {
              "walk_name": "Historic Bristol",
              "description": "Explore the historical sites of Bristol",
              "waypoints": [
                {
                  "waypoint_name": "Bristol Cathedral",
                  "description": "A magnificent Gothic cathedral dating back to the 12th century"
                },
                {
                  "waypoint_name": "St. Mary Redcliffe Church",
                  "description": "An impressive Anglican parish church known for its beautiful architecture"
                },
                {
                  "waypoint_name": "Bristol Old Town",
                  "description": "Walk through the charming streets of the old town and admire the preserved medieval buildings"
                }
              ]
            },
            {
              "walk_name": "Harbourside Stroll",
              "description": "Enjoy a scenic walk along Bristol's Harbourside",
              "waypoints": [
                {
                  "waypoint_name": "SS Great Britain",
                  "description": "Visit the famous steamship designed by Isambard Kingdom Brunel, now a museum"
                },
                {
                  "waypoint_name": "M Shed",
                  "description": "A museum dedicated to the history and culture of Bristol"
                },
                {
                  "waypoint_name": "Brunel's Suspension Bridge",
                  "description": "Marvel at the iconic Clifton Suspension Bridge, an engineering masterpiece"
                }
              ]
            },
            {
              "walk_name": "Street Art Exploration",
              "description": "Discover Bristol's vibrant street art scene",
              "waypoints": [
                {
                  "waypoint_name": "Nelson Street",
                  "description": "Walk along Nelson Street, a renowned spot for street art"
                },
                {
                  "waypoint_name": "Upfest Gallery",
                  "description": "Visit Europe's largest street art gallery and admire the impressive murals"
                },
                {
                  "waypoint_name": "Banksy's 'The Mild Mild West'",
                  "description": "See the famous Banksy artwork depicting a teddy bear throwing a Molotov cocktail"
                }
              ]
            }
          ]
        }
            """;
    @Autowired
    OpenAIService openAIService;

//    List<OpenAIRouteDTO> getListOfCorrectRoutes = openAIService.getListOfRoute(BRISTOL_CHAT_GPT_RESPONSE);

//    @BeforeAll
//    public void assignValue(){
//        getListOfCorrectRoutes = openAIService.getListOfRoute(BRISTOL_CHAT_GPT_RESPONSE);
//    }
//
    @Test
    public void getListOfRouteShouldReturnCorrectRouteList() {
        List<OpenAIRouteDTO> getListOfCorrectRoutes = openAIService.getListOfRoute(BRISTOL_CHAT_GPT_RESPONSE);
        assertEquals( 3 , getListOfCorrectRoutes.size());
        assertEquals("Historic Bristol", getListOfCorrectRoutes.get(0).name());
        assertEquals("Enjoy a scenic walk along Bristol's Harbourside", getListOfCorrectRoutes.get(1).description());
    }

    @Test
    public void getListOfRouteShouldReturnCorrectWaypointList() {
        List<OpenAIRouteDTO> getListOfCorrectRoutes = openAIService.getListOfRoute(BRISTOL_CHAT_GPT_RESPONSE);
        assertEquals("Enjoy a scenic walk along Bristol's Harbourside", getListOfCorrectRoutes.get(1).description());
        assertEquals( "Nelson Street" , getListOfCorrectRoutes.get(2).waypoints().get(0).name());
        assertEquals( "A museum dedicated to the history and culture of Bristol" , getListOfCorrectRoutes.get(1).waypoints().get(1).description());
    }




}
