package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DTO.OpenAIMessageDTO;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public record OpenAIRequestDTO(String model , @JsonProperty("messages") List<OpenAIMessageDTO> messages) {

    public OpenAIRequestDTO(String model, String prompt) {
        this(model, new ArrayList<>());
        messages.add(new OpenAIMessageDTO("user", prompt));
    }
}


