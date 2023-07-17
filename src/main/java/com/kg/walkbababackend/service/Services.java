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
        String response =  openAIService.chat(String.format("give me 5 different %s hour walk route in %s" +
                " Format: one json containing five results" +
                " Json response contains : \"walk_name\", \"description\", \"exact_duration\", \"distance\", \"waypoints_names\"",
        requestDTO.duration(), requestDTO.city()));
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
