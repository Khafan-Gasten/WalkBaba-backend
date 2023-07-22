package com.kg.walkbababackend.model.openai.DTO.directionsApi;

import java.util.ArrayList;

public class GeocodedWaypoint{
    public String geocoder_status;
    public boolean partial_match;
    public String place_id;
    public ArrayList<String> types;
}
