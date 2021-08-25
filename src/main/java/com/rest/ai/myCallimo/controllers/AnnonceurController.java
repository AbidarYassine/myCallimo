package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.dto.AnnonceurDto;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("annonceur")
public class AnnonceurController {

    private final AnnonceurService annonceurService;

    public AnnonceurController(AnnonceurService annonceurService) {
        this.annonceurService = annonceurService;
    }

    public AnnonceurDto findByTelephone(String telephone) {
        return annonceurService.findByTelephone(telephone);
    }

    @PutMapping("/update")
    public AnnonceurDto update(@RequestBody() AnnonceurDto annonceurDto) {
        return annonceurService.update(annonceurDto);
    }

    public AnnonceurDto save(AnnonceurDto annonceurDto) {
        return annonceurService.save(annonceurDto);
    }

    public List<AnnonceurDto> findAll() {
        return annonceurService.findAll();
    }

    public AnnonceurDto findById(Integer id) {
        return annonceurService.findById(id);
    }
}
