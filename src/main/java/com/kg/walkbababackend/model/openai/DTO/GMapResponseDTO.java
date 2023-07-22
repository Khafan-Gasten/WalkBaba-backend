package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties
public record GMapResponseDTO(String[] html_attributions, Result result, String status) {
    public record Result(List<GMapImageDTO> photos) {}
}
