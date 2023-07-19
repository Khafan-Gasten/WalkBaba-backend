package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.repo.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {

    @Autowired
    RouteRepository repo;
    public List<RouteInfo> saveRoute(List<OpenAIRouteDTO> routes, UserRequestDTO userRequest) {
        return routes.stream()
                .map(route -> convertToEntity(route, userRequest))
                .map(route -> repo.saveRoute(route))
                .toList();
    }

    public RouteInfo convertToEntity(OpenAIRouteDTO route, UserRequestDTO userRequest) {
        return new RouteInfo(userRequest, route);
    }
}
