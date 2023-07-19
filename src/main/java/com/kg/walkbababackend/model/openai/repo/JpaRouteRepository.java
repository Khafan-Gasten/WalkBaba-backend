package com.kg.walkbababackend.model.openai.repo;


import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRouteRepository extends JpaRepository<RouteInfo, Long> {
    List<RouteInfo> findByCityAndCountry(String city, String country);
}
