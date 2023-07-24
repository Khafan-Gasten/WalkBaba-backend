package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.UserInfo;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.Saving.SaveRouteRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ServicesTest {

    @Autowired
    Services services;

    @Test
    void createUserShouldWorkCorrectly() {
        UserInfo mahboob = services.createUser("Max", "1234");
        assertEquals(mahboob.getUserName(),"Max");
        assertEquals(mahboob.getPassword(), "1234");
    }
    @Test
    void setUserSaveRouteShouldSaveCorrectlyForSpecificUser() {
//        UserInfo userInfo = services.createUser("Max", "13642");
        SaveRouteRequestDTO saveRouteRequestDTO = new SaveRouteRequestDTO(1, 1);
        RouteToFrontEndDTO routeToFrontEndDTO = services.setUserSaveRoute(saveRouteRequestDTO);
        assertEquals(routeToFrontEndDTO.routeId(), 1);
    }



}