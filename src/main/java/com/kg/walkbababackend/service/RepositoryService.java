package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DB.UserInfo;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.Repo.RouteRepository;
import com.kg.walkbababackend.model.openai.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {

    @Autowired
    RouteRepository routeRepo;

    @Autowired
    UserRepository userRepo;

    public List<RouteInfo> saveRoute(List<RouteToFrontEndDTO> routes) {
        return routes.stream()
                .map(route -> new RouteInfo(route))
                .map(route -> routeRepo.saveRoute(route))
                .toList();
    }

    public List<RouteInfo> getRoutesFromDB(UserRequestDTO requestDTO) {
        return routeRepo.getRoutesFromDB(requestDTO);
    }

    public RouteToFrontEndDTO setUserSaveRoute(long userId, long routeId) {
        RouteInfo routeInfo = routeRepo.getRouteById(routeId);
        List<RouteInfo> routeInfos = userRepo.getUserById(userId).getSaveRoute();
        routeInfos.add(routeInfo);
        return new RouteToFrontEndDTO(routeInfo);
    }

    public List<RouteToFrontEndDTO> getUserSavedRouteFromDB(long userId) {
        return userRepo.getSavedRoute(userId).stream()
                .map(routeInfo -> new RouteToFrontEndDTO(routeInfo))
                .toList();
    }

    public UserInfo createUser(String userName, String password) {
        UserInfo user = new UserInfo(userName, password);
        return userRepo.saveUser(user);
    }
}
