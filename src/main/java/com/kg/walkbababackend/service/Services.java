package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DB.UserInfo;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.Saving.SaveRouteRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Services {
    @Autowired
    OpenAIService openAIService;

    @Autowired
    GoogleApiService googleApiService;

    @Autowired
    RepositoryService repoService;

    public List<RouteToFrontEndDTO> getRoutes(UserRequestDTO requestDTO) {
        List<RouteInfo> routesFromDB = repoService.getRoutesFromDB(requestDTO);
        if (!routesFromDB.isEmpty()) {
            List<RouteToFrontEndDTO> routesReformat = routesFromDB.stream()
                    .map(RouteToFrontEndDTO::new)
                    .collect(Collectors.toList());
            return addCityAndCountryDetails(routesReformat, requestDTO);
        }
        List<OpenAIRouteDTO> routes = openAIService.getOpenAIResponse(requestDTO);
        List<RouteToFrontEndDTO> routesToRender = googleApiService.getRoutesToRender(routes, requestDTO);
        routesToRender = repoService.saveRoute(routesToRender).stream()
                .map(RouteToFrontEndDTO::new)
                .collect(Collectors.toList());
        return addCityAndCountryDetails(routesToRender, requestDTO);
    }

    public List<RouteToFrontEndDTO> addCityAndCountryDetails(List<RouteToFrontEndDTO> routeToFrontEndDTOList, UserRequestDTO requestDTO) {
        routeToFrontEndDTOList.forEach(route -> route.waypoints()
                .replaceAll(waypoint -> waypoint.withCityAndCountry(waypoint.name(), requestDTO.city(), requestDTO.country())));
        return routeToFrontEndDTOList;
    }

    public RouteToFrontEndDTO setUserSaveRoute(SaveRouteRequestDTO saveRouteRequest) {
        return repoService.setUserSaveRoute(saveRouteRequest.id() , saveRouteRequest.routeId() );
    }

    public List<RouteToFrontEndDTO> getUserSavedRoute(long userId) {
        return repoService.getUserSavedRouteFromDB(userId);
    }

    public UserInfo createUser(String userName, String password){
        return repoService.createUser(userName, password);
    }

}
