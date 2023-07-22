package com.kg.walkbababackend.model.openai.DTO.OpenAi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DB.RouteInfo;

import java.util.List;
import java.util.stream.Collectors;

public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String description,
                             @JsonProperty("waypoints") List<WaypointDTO> waypoints ) {
    public OpenAIRouteDTO(RouteInfo routeInfo) {
        this(routeInfo.getRouteName(),
                routeInfo.getRouteDescription(),
                routeInfo.getWaypoints().stream()
                        .map(waypoint -> new WaypointDTO(waypoint))
                        .collect(Collectors.toList()));
    }

    public OpenAIRouteDTO(@JsonProperty("walk_name") String name, String description, @JsonProperty("waypoints") List<WaypointDTO> waypoints) {
        this.name = name;
        this.description = description;
        this.waypoints = waypoints;
    }
}
