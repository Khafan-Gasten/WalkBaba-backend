package com.kg.walkbababackend.model.openai.DTO.directionsApi;

import java.util.ArrayList;

public class Root{
    public ArrayList<GeocodedWaypoint> geocoded_waypoints;
    public ArrayList<Route> routes;
    public String status;
}
