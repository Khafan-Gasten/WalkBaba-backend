package com.kg.walkbababackend.controller;

import com.kg.walkbababackend.model.openai.DB.UserInfo;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.Saving.SaveRouteRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.Saving.UserDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    Services services;

    @PostMapping(value = "/openai")
    public ResponseEntity<List<RouteToFrontEndDTO>> getOpenAIResponse(@RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(services.getRoutes(requestDTO));
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("KG is the best fucking mob. Boo JP");
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<UserInfo> postSaveRoute(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(services.createUser(userDTO.userName(), userDTO.password()));
    }

    @PostMapping(value = "/saveroute")
    public ResponseEntity<RouteToFrontEndDTO> postSaveRoute(@RequestBody SaveRouteRequestDTO saveRouteRequest){
        System.out.println("in post route");
        return ResponseEntity.ok(services.setUserSaveRoute(saveRouteRequest));
    }


    @GetMapping(value = "/saveroute")
    public ResponseEntity<List<RouteToFrontEndDTO>> getUserSavedRoute(@RequestParam long userId){
        return ResponseEntity.ok(services.getUserSavedRoute(userId));
    }

    @GetMapping(value="/routes/map")
    public ResponseEntity<RouteToFrontEndDTO> getRouteById(@RequestParam long routeId) {
        return ResponseEntity.ok(services.getRouteById(routeId));
    }

    @DeleteMapping("/saveroute")
    public ResponseEntity<Void> deleteCart(@RequestBody SaveRouteRequestDTO saveRouteRequest) {
        services.deleteUserSavedRoute(saveRouteRequest);
        return ResponseEntity.noContent().build();
    }
}
