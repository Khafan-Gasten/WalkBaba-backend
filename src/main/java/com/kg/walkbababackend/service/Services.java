package com.kg.walkbababackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.walkbababackend.model.openai.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Services {
    @Autowired
    OpenAIService openAIService;

    public List<OpenAIRouteDTO> getOpenAIResponse(UserRequestDTO requestDTO) {
        String response =  openAIService.chat(String.format(
                "Give me 5 different walking routes in %s. " +
                        "They should be around %s hour in length and visit some highlights." +
                        " Calculate the exact duration in minutes of the route and " +
                        "respond with one json object containing five routes " +
                        "with the keys \"walk_name\", \"description\", \"duration\", \"distance, \"waypoints_names\""
                , requestDTO.city(),requestDTO.duration()));
        List<OpenAIRouteDTO> openAIRouteDTOList = getListOfRoute(response) ;
        return  openAIRouteDTOList ;
    }

    public  List<OpenAIRouteDTO> getListOfRoute(String response)  {
        response = response.substring(response.indexOf("["),response.lastIndexOf("]")+1) ;
        ObjectMapper mapper = new ObjectMapper();
        OpenAIRouteDTO[] myObjects = new OpenAIRouteDTO[0];
        try {
            myObjects = mapper.readValue(response, OpenAIRouteDTO[].class);
        } catch (JsonProcessingException e) {
            System.out.println( e.getMessage());
        }
        return Arrays.asList(myObjects) ;

    }
}
