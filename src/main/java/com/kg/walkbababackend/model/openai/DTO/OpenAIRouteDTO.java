package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String description,
                             @JsonProperty("duration") String duration,
                             String distance,
                             @JsonProperty("waypoint_names") List<String> waypoints ) {
}
