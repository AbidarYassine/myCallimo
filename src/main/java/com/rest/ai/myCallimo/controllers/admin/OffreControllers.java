package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.AffectationOffreRequest;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.UserResponse;
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

    @Autowired
    public OffreControllers(OffreService offreService) {
        this.offreService = offreService;
    }

    @PostMapping("/affectation-supervisor")
    public ResponseEntity<SupervisorResponse> affecterOffreToSupervisor(@Valid @RequestBody() AffectationOffreRequest affectationOffreRequest) {
        SupervisorDto supervisorDto = offreService.affecterOffreToSupervisor(affectationOffreRequest);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, SupervisorResponse.class), HttpStatus.OK);
    }

    @PostMapping("/affectation-caller")
    public ResponseEntity<CallerResponse> affecterOffreToCaller(@Valid @RequestBody() AffectationOffreRequest affectationOffreRequest) {
        CallerDto callerDto = offreService.affecterOffreToCaller(affectationOffreRequest);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(callerDto, CallerResponse.class), HttpStatus.OK);
    }

    @PostMapping()
    public OffreDto save(OffreDto offreDto) {
        return offreService.save(offreDto);
    }

    //    get all offre with annonceur contain telephone
    @GetMapping
    public List<OffreDto> findAll() {
        return offreService.findAll().stream()
                .filter
                        (el -> el.getAnnonceur() != null
                                && el.getAnnonceur().getTelephone() != null
                                && !el.getAnnonceur().getTelephone().equals("")
                                && !el.is_affected_to_caller()
                                && !el.is_affected_to_supervisor())
                .collect(Collectors.toList());
    }

    @GetMapping("/affected")
    public List<OffreDto> getOffreAfected() {
        return offreService.findAll().stream()
                .filter
                        (el -> el.getAnnonceur() != null
                                && el.getAnnonceur().getTelephone() != null
                                && !el.getAnnonceur().getTelephone().equals("")
                                && el.is_affected_to_caller()
                                || el.is_affected_to_supervisor())
                .collect(Collectors.toList());
    }

    //    find By Id
    @GetMapping("/id/{id}")
    public OffreDto findById(@PathVariable("id") Integer id) {
        return offreService.findById(id);
    }


    //    get offre affected to supervisor
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/supervisor/{id}")
    public List<OffreDto> getBySupervisor(@PathVariable() Integer id) {
        return offreService.getBySupervisor(id);
    }

    //    get offre affected to callers
    @PreAuthorize("hasRole('ADMIN') or hasRole('CALLER')")
    @GetMapping("/caller/{id}")
    public List<OffreDto> getByCaller(@PathVariable() Integer id) {
        return offreService.getByCaller(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/offre/{id}")
    public ResponseEntity<UserResponse> getByOffre(@PathVariable() Integer id) {
        UserDto userDto = offreService.getByOffre(id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(userDto, UserResponse.class), HttpStatus.OK);
    }
}
