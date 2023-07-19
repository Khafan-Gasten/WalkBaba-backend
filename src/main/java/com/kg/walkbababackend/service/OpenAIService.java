package com.kg.walkbababackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAIResponseDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OpenAIService {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    public List<OpenAIRouteDTO> getOpenAIResponse(UserRequestDTO requestDTO) {
        String response = chat(String.format(
                "Can you give me 5 walking tour routes with below details:\n" +
                        "- in the city of %s, %s\n" +
                        "- contain highlights saved as waypoints\n" +
                        "- each waypoint should have a short description\n" +
                        "- the routes should be of varying lengths\n" +
                        "Give the response as a json object with keys of \"walk_name\", \"description\", \"waypoints\". \"waypoints\" should include the keys \"waypoint_name\" and \"description\"."
                , requestDTO.city(), requestDTO.country()));
        return getListOfRoute(response);
    }

    public List<OpenAIRouteDTO> getListOfRoute(String response) {
        try {
            response = response.substring(response.indexOf("["), response.lastIndexOf("]") + 1);
            System.out.println("Maboo's bullshit\n"+response);
            ObjectMapper mapper = new ObjectMapper();
            OpenAIRouteDTO[] routeDTOS = mapper.readValue(response, OpenAIRouteDTO[].class);
            return Arrays.asList(routeDTOS);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Not fit to OpenAIRouteDTO !!!");
        } catch (StringIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Not contain list !!!");
        }
    }

    public String chat(String prompt) {


        // create a request
        OpenAIRequestDTO request = new OpenAIRequestDTO(model, prompt);

        // call the API
        OpenAIResponseDTO response = restTemplate.postForObject(apiUrl, request, OpenAIResponseDTO.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            throw new NoSuchElementException("No response from MaBoo BFF");
        }

        // return the first response
        System.out.println(response.choices().get(0).messageDTO().content());
        return response.choices().get(0).messageDTO().content();
    }
}
