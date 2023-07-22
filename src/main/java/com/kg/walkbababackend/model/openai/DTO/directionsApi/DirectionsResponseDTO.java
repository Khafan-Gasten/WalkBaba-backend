package com.kg.walkbababackend.model.openai.DTO.directionsApi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DirectionsResponseDTO(@JsonProperty("geocoded_waypoints") List<GeocodedWaypoint>  geocodedWaypointList,
                                    List<Route> routes,
                                    String status) {
}
