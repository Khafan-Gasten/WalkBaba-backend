package com.kg.walkbababackend.model.openai.DTO.Saving;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SaveRouteRequestDTO(@JsonProperty("id") long id,@JsonProperty("route_id") long routeId) {

}
