package com.kg.walkbababackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIResponseDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
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

    private static final String CHATGPT_PROMPT_TEMPLATE =
         "Can you give me walking tour routes with the below details:\n" +
                 "- in the city of Rotterdam, Netherlands\n" +
                 "- contain highlights saved as waypoints\n" +
                 "- each waypoint should have a short description\n" +
                 "- the routes should be of varying lengths\n" +
                 "- the response should be in these 6 theme : Nature, Architecture, Food,Culture, Top Highlights, Family fun\n" +
                 "-  recommend as much as you can\n" +
                 "- Give the response as one Json object. It should have  \"walk_name\", \"description\", \"waypoints\", " +
                 "\"theme\", as keys and \"waypoints\" should include the keys \"waypoint_name\" and \"description\"." +
                 "This is very important do this." ;
    public List<OpenAIRouteDTO> getOpenAIResponse(UserRequestDTO requestDTO) {
        String response = chat(String.format(CHATGPT_PROMPT_TEMPLATE, requestDTO.city(), requestDTO.country()));
        return getListOfRoute(response);
    }

    public List<OpenAIRouteDTO> getListOfRoute(String response) {
        try {
            System.out.println( "gpt response*********\n " + response);
            //response = "[" + response + "]";
            response = response.substring(response.indexOf("["), response.lastIndexOf("]") + 1);
            System.out.println("Maboo's bullshit\n"+response);
            ObjectMapper mapper = new ObjectMapper();
            OpenAIRouteDTO[] routeDTOS = mapper.readValue(response, OpenAIRouteDTO[].class);
            return Arrays.asList(routeDTOS);
        } catch (JsonProcessingException e) {
            //Could we catch this and send it back to chatGPT and ask it to put it in Json format - how do you keep the conversion.
            throw new IllegalArgumentException("Json for chatGPT incorrect format for OpenAIRouteDTO!!!");
        } catch (StringIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("ChatGPT response is not a list list!!!");
        }
    }

    //We can test this!! Just check that the api is responding then check for regex
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
