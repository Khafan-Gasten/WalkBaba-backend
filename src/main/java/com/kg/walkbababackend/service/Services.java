package com.kg.walkbababackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.repo.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Services {
    @Autowired
    OpenAIService openAIService;

    @Autowired
    RepositoryService repoService;

    @Autowired
    RouteRepository repo;


    public List<OpenAIRouteDTO> getRoutes(UserRequestDTO requestDTO) {
//        List<RouteInfo> routesFromDB = repo.getRoutesFromDB(requestDTO);
//        if (routesFromDB != null) {
//            return addCityAndCountryDetails(routesFromDB.stream().map(route -> new OpenAIRouteDTO(route)).toList(), requestDTO);
//        }
        List<OpenAIRouteDTO> routes = openAIService.getOpenAIResponse(requestDTO);
//        repoService.saveRoute(routes, requestDTO);
        return addCityAndCountryDetails(routes, requestDTO);
    }

    public List<OpenAIRouteDTO> addCityAndCountryDetails(List<OpenAIRouteDTO> openAIRouteDTOList, UserRequestDTO requestDTO) {
        openAIRouteDTOList.forEach(route -> route.waypoints()
                .forEach(waypoint -> waypoint.withName(waypoint.name(), requestDTO.city(), requestDTO.country())));
        return openAIRouteDTOList;
    }
}
