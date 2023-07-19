package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WaypointDTO(@JsonProperty("waypoint_name") String name, String description){
    public WaypointDTO withName(String name, String city, String country) {
        return new WaypointDTO(String.format("%s, %s, %s", name, city, country), this.description);
    }
}
