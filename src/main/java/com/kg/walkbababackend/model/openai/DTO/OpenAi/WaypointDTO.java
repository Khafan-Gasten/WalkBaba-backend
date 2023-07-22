package com.kg.walkbababackend.model.openai.DTO.OpenAi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DB.WaypointInfo;

import java.util.List;

@JsonIgnoreProperties
public record WaypointDTO(@JsonProperty("waypoint_name") String name, String description , List<String> imageLink){
    public WaypointDTO withCityAndCountry(String name, String city, String country) {
        return new WaypointDTO(String.format("%s, %s, %s", name, city, country),
                this.description , this.imageLink );
    }
    public WaypointDTO(WaypointInfo waypoint) {
        this(waypoint.getWaypointName(), waypoint.getWaypointDescription() , waypoint.getImageLink());
    }


}
