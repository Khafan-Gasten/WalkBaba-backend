package com.kg.walkbababackend.service;

import com.kg.walkbababackend.model.openai.DTO.GMapImageDTO;
import com.kg.walkbababackend.model.openai.DTO.GMapResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GoogleMapService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${googleMap.api.key}")
    String apiKey;

    @Value("${googleMap.imageMaxWidth}")
    Long maxWidth;

    @Value("${googleMap.api.url}")
    String url;


    public List<String> getPlaceImageUrl(String placeId){
        String requestUrl = String.format("%s/place/details/json" +
                "?fields=photos" +
                "&place_id=%s" +
                "&key=%s",url,placeId, apiKey) ;
        GMapResponseDTO responseDTO = restTemplate.getForObject(requestUrl, GMapResponseDTO.class);
        return responseDTO.result().photos()
                .stream()
                .limit(5)
                .map(photo -> String.format("%s/place/photo" +
                "?maxwidth=%d" +
                "&photo_reference=%s" +
                "&key=%s",url, maxWidth, photo.photoReference(), apiKey)).toList();

    }
}
