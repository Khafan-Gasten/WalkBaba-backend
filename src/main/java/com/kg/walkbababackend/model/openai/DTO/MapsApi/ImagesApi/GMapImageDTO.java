package com.kg.walkbababackend.model.openai.DTO.MapsApi.ImagesApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties
public record GMapImageDTO(int height, @JsonProperty("html_attributions") String[] htmlAttributions,
                           @JsonProperty("photo_reference") String photoReference, int width) {

}
