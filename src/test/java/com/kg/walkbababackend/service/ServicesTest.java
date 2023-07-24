package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.UserInfo;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.Saving.SaveRouteRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        SaveRouteRequestDTO saveRouteRequestDTO = new SaveRouteRequestDTO(1, 5);
        RouteToFrontEndDTO routeToFrontEndDTO = services.setUserSaveRoute(saveRouteRequestDTO);
        assertEquals(routeToFrontEndDTO.routeId(), 5);
    }

    @Test
    void getUserSavedRouteFromDBShouldReturn5() {
        List<RouteToFrontEndDTO> routeToFrontEndDTOs = services.getUserSavedRoute(1);
        assertEquals(routeToFrontEndDTOs.size(), 5);
    }
}
