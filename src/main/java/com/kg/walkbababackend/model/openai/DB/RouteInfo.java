package com.kg.walkbababackend.model.openai.DB;


import jakarta.persistence.*;


@Entity
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="corn_hund")
    private long id;
}
