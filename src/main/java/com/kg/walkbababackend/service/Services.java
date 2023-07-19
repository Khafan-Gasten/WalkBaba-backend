package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.Repo.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Services {
    @Autowired
    OpenAIService openAIService;

    @Autowired
    RepositoryService repoService;

    @Autowired
    RouteRepository repo;
    public List<OpenAIRouteDTO> getRoutes(UserRequestDTO requestDTO) {
        List<RouteInfo> routesFromDB = repo.getRoutesFromDB(requestDTO);
        //Is the list from the database immutable?
        if (routesFromDB.size() != 0) {
            ArrayList<RouteInfo> cloneDB = new ArrayList<>();
            for(RouteInfo route: routesFromDB) {
                cloneDB.add(route.clone());
            }
//            System.out.println("Did it from DB");
//            routesFromDB.forEach(route -> System.out.println(route.getRouteName()));
            OpenAIRouteDTO[] routesReformat = cloneDB.stream().map(OpenAIRouteDTO::new).toArray(OpenAIRouteDTO[]::new);
            System.out.println(routesReformat.toString());
            ArrayList<OpenAIRouteDTO> routesMoreReformat = new ArrayList<OpenAIRouteDTO>(Arrays.asList(routesReformat));
            return routesMoreReformat;
//            return addCityAndCountryDetails(routesMoreReformat, requestDTO);
        }
        System.out.println("Did it from GPT");
        List<OpenAIRouteDTO> routes = openAIService.getOpenAIResponse(requestDTO);
        repoService.saveRoute(routes, requestDTO);
        return addCityAndCountryDetails(routes, requestDTO);
    }

    public List<OpenAIRouteDTO> addCityAndCountryDetails(List<OpenAIRouteDTO> openAIRouteDTOList, UserRequestDTO requestDTO) {
        openAIRouteDTOList.forEach(route -> route.waypoints()
                .replaceAll(waypoint -> waypoint.withCityAndCountry(waypoint.name(), requestDTO.city(), requestDTO.country())));
        return openAIRouteDTOList;
    }
}
