package com.kg.walkbababackend.model.openai.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.WaypointDTO;
import com.kg.walkbababackend.model.openai.DTO.directionsApi.DirectionsResponseDTO;

import java.util.List;

public record RouteToFrontEndDTO(List<OpenAIRouteDTO> openAIRouteDTO,
                                 List<DirectionsResponseDTO> geocodedWaypoints) {
    public RouteToFrontEndDTO(List<OpenAIRouteDTO> openAIRouteDTO, List<DirectionsResponseDTO> geocodedWaypoints) {
        this.openAIRouteDTO = openAIRouteDTO;
        this.geocodedWaypoints = geocodedWaypoints;
    }
}
