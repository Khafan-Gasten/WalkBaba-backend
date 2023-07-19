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

    public List<OpenAIRouteDTO> getOpenAIResponse(UserRequestDTO requestDTO)  {
        String response =  openAIService.chat(String.format(
                "Give me 5 different walking routes in %s, %s. " +
                        "They should be around %s hour in length and visit some highlights." +
                        " Calculate the exact duration in minutes of the route and " +
                        "respond with one json object containing five routes " +
                        "with the keys \"walk_name\", \"description\", \"duration\", \"distance\", \"waypoint_names\""
                , requestDTO.city(), requestDTO.country(), requestDTO.duration()));
        List<OpenAIRouteDTO> openAIRouteDTOList = getListOfRoute(response) ;

        openAIRouteDTOList.forEach(route -> route.waypoints()
                .replaceAll(waypoint -> String.format("%s, %s, %s",waypoint,requestDTO.city(),requestDTO.country())
                )
        );
        return  openAIRouteDTOList ;
    }

    public  List<OpenAIRouteDTO> getListOfRoute(String response) {
        try {
            response = response.substring(response.indexOf("["),response.lastIndexOf("]")+1) ;
            ObjectMapper mapper = new ObjectMapper();
            OpenAIRouteDTO[] routeDTOS = mapper.readValue(response, OpenAIRouteDTO[].class);
            return Arrays.asList(routeDTOS) ;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Not fit to OpenAIRouteDTO !!!");
        } catch (StringIndexOutOfBoundsException ex){
            throw new IllegalArgumentException("Not contain list !!!");
        }

    }
}
