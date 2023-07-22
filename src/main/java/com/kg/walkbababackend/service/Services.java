package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.directionsApi.DirectionsResponseDTO;
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

    public List<OpenAIRouteDTO> getRoutes(UserRequestDTO requestDTO) {
        List<RouteInfo> routesFromDB = repoService.getRoutesFromDB(requestDTO);
        if (!routesFromDB.isEmpty()) {
            List<OpenAIRouteDTO> routesReformat = routesFromDB.stream()
                    .map(OpenAIRouteDTO::new)
                    .collect(Collectors.toList());
            return addCityAndCountryDetails(routesReformat, requestDTO);
        }
        List<OpenAIRouteDTO> routes = openAIService.getOpenAIResponse(requestDTO);
        List<DirectionsResponseDTO> routesToRender = googleApiService.getRoutesToRender(routes, requestDTO);
        RouteToFrontEndDTO routeToFrontEndDTO = new RouteToFrontEndDTO(routes, routesToRender);
        //Call unsplash or (google maps image api?) to add images to the routes (all the waypoints and the route).
        //repoService.saveRoute(routes, requestDTO);
        return routes;
    }

    public List<OpenAIRouteDTO> addCityAndCountryDetails(List<OpenAIRouteDTO> openAIRouteDTOList, UserRequestDTO requestDTO) {
        openAIRouteDTOList.forEach(route -> route.waypoints()
                .replaceAll(waypoint -> waypoint.withCityAndCountry(waypoint.name(), requestDTO.city(), requestDTO.country())));
        return openAIRouteDTOList;
    }
}
