package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record UserRequestDTO(String country, String city, String duration, String theme, Boolean roundTrip) {

    public UserRequestDTO(String country, String city, String duration, String theme, Boolean roundTrip) {
        this.country = country;
        this.city = city;
        this.duration = duration;
        this.theme = theme;
        this.roundTrip = roundTrip;
    }
}
