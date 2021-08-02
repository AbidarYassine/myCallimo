package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.request.AffectationOffreRequest;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.services.facade.OffreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/admin/offres")
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class OffreControllers {
    private final OffreService offreService;

    @PostMapping("/affectation")
    public ResponseEntity<SupervisorResponse> affecterOffreToSupervisor(@Valid @RequestBody() AffectationOffreRequest affectationOffreRequest) {
        SupervisorDto supervisorDto = offreService.affecterOffreToSupervisor(affectationOffreRequest);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, SupervisorResponse.class), HttpStatus.OK);
    }

    @Autowired
    public OffreControllers(OffreService offreService) {
        this.offreService = offreService;
    }

    @PostMapping()
    public OffreDto save(OffreDto offreDto) {
        return offreService.save(offreDto);
    }

    //    get all offre with annonceur contain telephone
    @GetMapping
    public List<OffreDto> findAll() {
        return offreService.findAll().stream()
                .filter(el -> el.getAnnonceur() != null && el.getAnnonceur().getTelephone() != null && !el.getAnnonceur().getTelephone().equals(""))
                .collect(Collectors.toList());
    }

    //    find By Id
    @GetMapping("id/{id}")
    public OffreDto findById(@PathVariable("id") Integer id) {
        return offreService.findById(id);
    }


}
