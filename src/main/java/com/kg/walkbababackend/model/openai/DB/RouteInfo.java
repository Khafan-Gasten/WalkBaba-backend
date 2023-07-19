package com.kg.walkbababackend.model.openai.DB;


import com.kg.walkbababackend.model.openai.DTO.WayPointDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String country;
    private String title;
    private String description;
    @ElementCollection
    private List<String> waypoints;
}
