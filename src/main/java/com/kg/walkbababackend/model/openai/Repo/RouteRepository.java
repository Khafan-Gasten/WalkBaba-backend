package com.kg.walkbababackend.model.openai.Repo;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteRepository{

    @Autowired
    JpaRouteRepository repo;

    public RouteInfo saveRoute(RouteInfo newRoute) {
        return repo.save(newRoute);
    }

    public List<RouteInfo> getRoutesFromDB(UserRequestDTO userRequest) {
        return repo.findByCityAndCountry(userRequest.city(), userRequest.country());
    }

    public RouteInfo getRouteById(long routeId) {
        return repo.findById(routeId).orElse(null);
    }
}
