package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import com.rest.ai.myCallimo.entities.base.CityBase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("/offers")
@RestController
public class OffreControllers {

    @GetMapping("")
    public List<AnnonceurBase> getTweetsBlocking() {
        String uri = "https://myspace.espaceo.net/api/get-pap-offers";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<AnnonceurBase>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AnnonceurBase>>() {
                });
        List<AnnonceurBase> result = response.getBody();
        return result;
    }
}
