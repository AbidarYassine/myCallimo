package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.dto.OffreTypeDto;
import com.rest.ai.myCallimo.services.facade.OffreTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("offre-type")
public class OffreTypeController {

    private OffreTypeService offreTypeService;

    public OffreTypeController(OffreTypeService offreTypeService) {
        this.offreTypeService = offreTypeService;
    }

    public OffreTypeDto save(OffreTypeDto offreTypeDto) {
        return offreTypeService.save(offreTypeDto);
    }

    @GetMapping()
    public List<OffreTypeDto> findAll() {
        return offreTypeService.findAll();
    }

    public OffreTypeDto findById(Integer id) {
        return offreTypeService.findById(id);
    }

    public OffreTypeDto findByType(String type) {
        return offreTypeService.findByType(type);
    }
}
