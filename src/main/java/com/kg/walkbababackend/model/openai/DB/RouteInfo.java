package com.kg.walkbababackend.model.openai.DB;


import com.kg.walkbababackend.model.openai.DTO.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
import com.kg.walkbababackend.model.openai.DTO.WaypointDTO;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class RouteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="route_id")
    private long routeId;
    private String city;
    private String country;
    private String routeName;
    private String routeDescription;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WaypointInfo> waypoints;

    public RouteInfo(UserRequestDTO userRequest, OpenAIRouteDTO openAIRouteDTO) {
        this.city = userRequest.city();
        this.country = userRequest.country();
        this.routeName = openAIRouteDTO.name();
        this.routeDescription = openAIRouteDTO.description();
        this.waypoints = openAIRouteDTO.waypoints().stream()
                .map(waypoint -> new WaypointInfo(waypoint))
                .toList();
    }

    public RouteInfo() {

    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public List<WaypointInfo> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointInfo> waypoints) {
        this.waypoints = waypoints;
    }
}
