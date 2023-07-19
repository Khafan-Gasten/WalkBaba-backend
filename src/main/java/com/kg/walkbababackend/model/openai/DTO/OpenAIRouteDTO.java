package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String description,
                             @JsonProperty("waypoints") List<WaypointDTO> waypoints ) {
    public record WaypointDTO(@JsonProperty("waypoint_name") String name, String description){
        public WaypointDTO withName(String name, String city, String country) {
            return new WaypointDTO(String.format("%s, %s, %s", name, city, country), this.description);
        }
    }
}
