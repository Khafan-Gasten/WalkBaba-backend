package com.kg.walkbababackend.model.openai.DB;


import jakarta.persistence.*;

@Entity
public class WaypointInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long waypointID;
    private String name;
    private String description;
    private String imageLink;
}
