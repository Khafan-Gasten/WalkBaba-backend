package com.kg.walkbababackend.model.openai.DB;


import jakarta.persistence.*;

import java.util.List;


@Entity
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long routeId;
    private String city;
    private String country;
    private String title;
    private String description;

    private List<String> waypoints;
}
