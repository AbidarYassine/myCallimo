package com.rest.ai.myCallimo.controllers;


import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import com.rest.ai.myCallimo.entities.base.OffreTypeBase;
import com.rest.ai.myCallimo.services.facade.OffreService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@RequestMapping("/offers")
@RestController
public class OffreControllers {
    private final OffreService offreService;

    @Autowired
    public OffreControllers(OffreService offreService) {
        this.offreService = offreService;
    }

    @PostMapping()
    public OffreEntity save(OffreEntity offreEntity) {
        return offreService.save(offreEntity);
    }

    @GetMapping
    public List<OffreEntity> findAll() {
        return offreService.findAll();
    }

    @GetMapping("id/{id}")
    public OffreEntity findById(@PathVariable("id") Integer id) {
        return offreService.findById(id);
    }

    @GetMapping("data")
    public List<OffreTypeBase> getOffreService() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OffreTypeBase[]> response = restTemplate.getForEntity("https://myspace.espaceo.net/api/get-pap-offers",
                OffreTypeBase[].class
        );
        OffreTypeBase[] annonceurBasetData = response.getBody();
        return Arrays.asList(annonceurBasetData);
    }

    @GetMapping("city")
    public List<CityBase> getCities() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CityBase[]> response = restTemplate.getForEntity("https://myspace.espaceo.net/api/get-pap-offers",
                CityBase[].class
        );
        CityBase[] annonceurBasetData = response.getBody();
        return Arrays.asList(annonceurBasetData);
    }

    @GetMapping("offre")
    public List<OffreBase> getOffre() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OffreBase[]> response = restTemplate.getForEntity("https://myspace.espaceo.net/api/get-pap-offers",
                OffreBase[].class
        );
        OffreBase[] offreBasetData = response.getBody();
        return Arrays.asList(offreBasetData);
    }
}
