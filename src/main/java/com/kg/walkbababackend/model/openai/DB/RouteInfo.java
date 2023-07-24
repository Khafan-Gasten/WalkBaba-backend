package com.kg.walkbababackend.model.openai.DB;


import com.kg.walkbababackend.model.openai.DTO.OpenAi.OpenAIRouteDTO;
import com.kg.walkbababackend.model.openai.DTO.RouteToFrontEndDTO;
import com.kg.walkbababackend.model.openai.DTO.UserRequestDTO;
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
    private String theme ;
    private Long distance;
    private Long durationInMin ;
    private Long likes ;
    private Long dislike ;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WaypointInfo> waypoints;

    public RouteInfo(RouteToFrontEndDTO routeToFrontEndDTO) {
        this.city = routeToFrontEndDTO.city();
        this.country = routeToFrontEndDTO.country();
        this.routeName = routeToFrontEndDTO.name();
        this.routeDescription = routeToFrontEndDTO.description();
        this.theme = routeToFrontEndDTO.theme();
        this.distance = routeToFrontEndDTO.distance();
        this.durationInMin = routeToFrontEndDTO.durationInMin();
        this.dislike = routeToFrontEndDTO.dislike();
        this.likes = routeToFrontEndDTO.like();
        this.waypoints = routeToFrontEndDTO.waypoints().stream()
                .map(WaypointInfo::new)
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(Long durationInMin) {
        this.durationInMin = durationInMin;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislike() {
        return dislike;
    }

    public void setDislike(Long dislike) {
        this.dislike = dislike;
    }
}
