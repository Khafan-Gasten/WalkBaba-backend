package com.kg.walkbababackend.model.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String description,
                             @JsonProperty("duration") String duration,
                             String distance,
                             @JsonProperty("waypoints") List<String> waypoints ) {
}
