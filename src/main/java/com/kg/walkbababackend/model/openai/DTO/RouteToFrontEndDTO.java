package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.MapsApi.ExportLinkDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RouteToFrontEndDTO(@JsonProperty("walk_name") String name,
                                 @JsonProperty("route_id") long routeId,
                                 String city,
                                 String country,
                                 String description,
                                 String theme,
                                 Double distance,
                                 Long durationInMin,
                                 Long like,
                                 Long dislike,
                                 ExportLinkDTO exportLinks,
                                 @JsonProperty("waypoints") List<WaypointDTO> waypoints) {
    public RouteToFrontEndDTO(RouteInfo routeInfo) {
        this(routeInfo.getRouteName(),
                routeInfo.getRouteId(),
                routeInfo.getCity(),
                routeInfo.getCountry(),
                routeInfo.getRouteDescription(),
                routeInfo.getTheme(),
                routeInfo.getDistance(),
                routeInfo.getDurationInMin(),
                routeInfo.getLikes(),
                routeInfo.getDislike(),
                new ExportLinkDTO(routeInfo.getExportLink()),
                routeInfo.getWaypoints().stream()
                        .map(waypoint -> new WaypointDTO(waypoint))
                        .collect(Collectors.toList()));
    }

    public RouteToFrontEndDTO(OpenAIRouteDTO openAIRouteDTO,
                          UserRequestDTO userRequestDTO,
                          Double totalDist,
                          Long totalDur,
                          ExportLinkDTO exportLinks,
                          List<WaypointDTO> waypoints) {
        this(openAIRouteDTO.name(),
                0L,
                userRequestDTO.city(),
                userRequestDTO.country(),
                openAIRouteDTO.description(),
                openAIRouteDTO.theme(),
                totalDist,
                totalDur,
                0L,
                0L,
                exportLinks,
                waypoints);
    }
}
