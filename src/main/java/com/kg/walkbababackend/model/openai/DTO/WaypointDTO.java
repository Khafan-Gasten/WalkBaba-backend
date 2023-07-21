package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DB.WaypointInfo;

public record WaypointDTO(@JsonProperty("waypoint_name") String name, String description){
    public WaypointDTO withCityAndCountry(String name, String city, String country) {
        return new WaypointDTO(String.format("%s, %s, %s", name, city, country), this.description);
    }
    public WaypointDTO(WaypointInfo waypoint) {
        this(waypoint.getWaypointName(), waypoint.getWaypointDescription());
    }

    public WaypointDTO(@JsonProperty("waypoint_name") String name, String description) {
        this.name = name;
        this.description = description;
    }
}
