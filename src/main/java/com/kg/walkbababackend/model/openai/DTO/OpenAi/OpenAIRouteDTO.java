package com.kg.walkbababackend.model.openai.DTO.OpenAi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String description,
                             String theme,
                             @JsonProperty("waypoints") List<WaypointDTO> waypoints) {
}


