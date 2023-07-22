package com.kg.walkbababackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GoogleMapServiceTest {
    @Autowired
    RestTemplate restTemplate;

    @Value("${googleMap.api.key}")
    String apiKey;

    @Value("${googleMap.imageMaxWidth}")
    Long maxWidth;

    @Value("${googleMap.api.url}")
    String url;

    @Autowired
    GoogleMapService googleMapService;

 @Test
    public void getPlaceImageUrlShouldReturnCorrectUrl(){
//        String expected = String.format("%s/place/photo" +
//                "?maxwidth=%d" +
//                "&photo_reference=Aaw_FcKRt2ZMupBxkEfuFQzemCTLipogaoGOj1kqt5NTYD4YURjxAM0lja-BAfqJzSFK-vcPOkJl5Lipg0afSUzElqFs-66YePrs_RuH9L9ZRQofqazwRLiqBXgI8DBvtlOpo1MTwjzHBGEt9Q-tttHwg5bYdJNcpIND5Z18X9wqwx_AF3wX" +
//                "&key=%s", url,maxWidth,apiKey);

        List<String> actual = googleMapService.getPlaceImageUrl("ChIJN1t_tDeuEmsRUsoyG83frY4");
//        assertEquals(expected, actual);
        assertNotNull(actual);
        assertTrue(actual.size() > 0);
    }

}