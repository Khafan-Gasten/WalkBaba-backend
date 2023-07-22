package com.kg.walkbababackend.model.openai.DTO.directionsApi;

public class Step{
    public Distance distance;
    public Duration duration;
    public EndLocation end_location;
    public String html_instructions;
    public Polyline polyline;
    public StartLocation start_location;
    public String travel_mode;
    public String maneuver;
}
