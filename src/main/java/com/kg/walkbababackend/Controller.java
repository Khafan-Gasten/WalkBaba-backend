package com.kg.walkbababackend;

import com.kg.walkbababackend.model.openai.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.UserRequestDTO;
import com.kg.walkbababackend.service.OpenAIService;
import com.kg.walkbababackend.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/openai")
    public ResponseEntity<List<OpenAIRouteDTO>> getOpenAIResponse(@RequestBody UserRequestDTO requestDTO) {

        return ResponseEntity.ok(services.getOpenAIResponse(requestDTO));



//        "Task:  Recommend 5 different  2 hour walking routes " +
//                "and estimated duration between points\n" +
//                "City: Rotterdam\n" +
//                "Format: one json containing five results\n" +
//                "Response Contain : walk name, description, duration, distance, waypoints names"));
    }
}
