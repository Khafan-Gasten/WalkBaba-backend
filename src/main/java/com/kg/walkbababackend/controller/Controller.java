package com.kg.walkbababackend.controller;

import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    Services services;

    @PostMapping(value = "/openai")
    public ResponseEntity<List<OpenAIRouteDTO>> getOpenAIResponse(@RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(services.getRoutes(requestDTO));
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("KG is the best fucking mob. Boo JP");
    }
}
