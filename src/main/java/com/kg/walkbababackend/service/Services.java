package com.kg.walkbababackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Services {
    @Autowired
    OpenAIService openAIService;

    public List<OpenAIRouteDTO> getOpenAIResponse(UserRequestDTO requestDTO) {
        String response = openAIService.chat(String.format(
                "Can you give me 5 walking tour routes with below details:\n" +
                        "- in the city of %s, %s\n" +
                        "- contain highlights saved as waypoints\n" +
                        "- each waypoint should have a short description\n" +
                        "- the routes should be of varying lengths\n" +
                        "Give the response as a json object with keys of \"walk_name\", \"description\", \"waypoints\". \"waypoints\" should include the keys \"waypoint_name\" and \"description\"."
                , requestDTO.city(), requestDTO.country()));
        List<OpenAIRouteDTO> openAIRouteDTOList = getListOfRoute(response);

        openAIRouteDTOList.forEach(route -> route.waypoints()
                .forEach(waypoint ->
                waypoint.withName(waypoint.name(), requestDTO.city(), requestDTO.country())));
        return openAIRouteDTOList;
    }

    public List<OpenAIRouteDTO> getListOfRoute(String response) {
        try {
            response = response.substring(response.indexOf("["), response.lastIndexOf("]") + 1);
            ObjectMapper mapper = new ObjectMapper();
            OpenAIRouteDTO[] routeDTOS = mapper.readValue(response, OpenAIRouteDTO[].class);
            return Arrays.asList(routeDTOS);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Not fit to OpenAIRouteDTO !!!");
        } catch (StringIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Not contain list !!!");
        }

    }
}
