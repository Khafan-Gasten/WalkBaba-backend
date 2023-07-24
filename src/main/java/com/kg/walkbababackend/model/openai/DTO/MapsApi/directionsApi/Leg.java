package com.kg.walkbababackend.model.openai.DTO.MapsApi.directionsApi;

import java.util.ArrayList;

public class Leg{
    public Distance distance;
    public Duration duration;
    public String end_address;
    public EndLocation end_location;
    public String start_address;
    public StartLocation start_location;
    public ArrayList<Step> steps;
    public ArrayList<Object> traffic_speed_entry;
    public ArrayList<ViaWaypoint> via_waypoint;
}
