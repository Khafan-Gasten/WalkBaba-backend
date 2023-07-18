package com.kg.walkbababackend;

import com.kg.walkbababackend.model.openai.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.UserRequestDTO;
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

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("KG is the best fucking mob. Boo JP");
    }

    @PostMapping(value = "/openai")
    public ResponseEntity<List<OpenAIRouteDTO>> getOpenAIResponse(@RequestBody UserRequestDTO requestDTO) {

        return ResponseEntity.ok(services.getOpenAIResponse(requestDTO));
    }
}
