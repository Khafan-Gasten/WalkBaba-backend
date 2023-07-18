package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.OpenAIMessageDTO;
import com.kg.walkbababackend.model.openai.OpenAIRequestDTO;
import com.kg.walkbababackend.model.openai.OpenAIResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    public String chat(String prompt) {
        // create a request
        OpenAIRequestDTO request = new OpenAIRequestDTO(model, prompt);

        // call the API
        OpenAIResponseDTO response = restTemplate.postForObject(apiUrl, request, OpenAIResponseDTO.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            return "No response";
        }

        // return the first response
        System.out.println(response.choices().get(0).messageDTO().content());
        return response.choices().get(0).messageDTO().content();
    }
}
