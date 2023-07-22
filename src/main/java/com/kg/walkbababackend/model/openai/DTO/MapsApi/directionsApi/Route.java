package com.kg.walkbababackend.model.openai.DTO.MapsApi.directionsApi;

import java.util.ArrayList;

public class Route{
    public Bounds bounds;
    public String copyrights;
    public ArrayList<Leg> legs;
    public OverviewPolyline overview_polyline;
    public String summary;
    public ArrayList<String> warnings;
    public ArrayList<Object> waypoint_order;
}
