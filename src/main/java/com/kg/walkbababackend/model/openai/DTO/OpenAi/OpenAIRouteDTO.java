package com.kg.walkbababackend.model.openai.DTO.OpenAi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DB.RouteInfo;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties
public record OpenAIRouteDTO(@JsonProperty("walk_name") String name,
                             String city,
                             String country,
                             String description,
                             String theme,
                             Long distance,
                             Long durationInMin,
                             Long like,
                             Long dislike,
                             @JsonProperty("waypoints") List<WaypointDTO> waypoints) {
    public OpenAIRouteDTO(RouteInfo routeInfo) {
        this(routeInfo.getRouteName(),
                routeInfo.getCity(),
                routeInfo.getCountry(),
                routeInfo.getRouteDescription(),
                routeInfo.getTheme(),
                routeInfo.getDistance(),
                routeInfo.getDurationInMin(),
                routeInfo.getLikes(),
                routeInfo.getDislike(),
                routeInfo.getWaypoints().stream()
                        .map(waypoint -> new WaypointDTO(waypoint))
                        .collect(Collectors.toList()));
    }

    public OpenAIRouteDTO(String name, String description, List<WaypointDTO> waypoints) {
        this(name,
                null,
                null,
                description,
                null,
                null,
                null,
                null,
                null,
                waypoints);
    }

}


