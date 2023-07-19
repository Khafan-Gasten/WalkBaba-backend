package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DTO.OpenAIMessageDTO;

import java.util.List;

public record OpenAIResponseDTO(List<Choice> choices){
    public record Choice(int index ,@JsonProperty("message") OpenAIMessageDTO messageDTO){ }
}
