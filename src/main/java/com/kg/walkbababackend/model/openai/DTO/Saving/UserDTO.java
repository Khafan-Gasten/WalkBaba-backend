package com.kg.walkbababackend.model.openai.DTO.Saving;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(@JsonProperty("user_name") String userName , String password) {
}
