package com.kg.walkbababackend.model.openai.DTO.OpenAi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIResponseDTO(List<Choice> choices){
    public record Choice(int index ,@JsonProperty("message") OpenAIMessageDTO messageDTO){ }
}
