package com.kg.walkbababackend.model.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String description,
                             @JsonProperty("exact_duration") String duration,
                             String distance,
                             @JsonProperty("waypoints_names") List<String> waypoints ) {
}
