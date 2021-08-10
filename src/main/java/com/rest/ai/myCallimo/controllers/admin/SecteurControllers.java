package com.rest.ai.myCallimo.controllers.admin;

import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.response.SecteurResponse;
import com.rest.ai.myCallimo.services.facade.SecteurService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j

@RequestMapping("/admin/secteurs")
public class SecteurControllers {
    @Autowired
    private SecteurService secteurService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<SecteurResponse> save(@Valid @RequestBody() SecteurDto secteurDto) {
        SecteurDto dto = secteurService.save(secteurDto);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("")
    public ResponseEntity<List<SecteurResponse>> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<SecteurResponse> secteurResponses =
                secteurService.findAll().stream()
                        .map(el -> modelMapper.map(el, SecteurResponse.class))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(secteurResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<SecteurResponse> findById(@PathVariable() Integer id) {
        SecteurDto dto = secteurService.findById(id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/libelle/{libelle}")
    public ResponseEntity<SecteurResponse> findByLibelle(@PathVariable() String libelle) {
        SecteurDto dto = secteurService.findByLibelle(libelle);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/code/{code}")
    public ResponseEntity<SecteurResponse> findByCode(@PathVariable() String code) {
        SecteurDto dto = secteurService.findByCode(code);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/cities/secteur/{id}")
    public ResponseEntity<List<CityDto>> getBySecteurId(@PathVariable() Integer id) {
        List<CityDto> list = secteurService.getBySecteurId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/non-afected")
    public ResponseEntity<List<SecteurResponse>> getSecteurNonAfecter() {
        ModelMapper modelMapper = new ModelMapper();
        List<SecteurResponse> secteurResponses =
                secteurService.getSecteurNonAfecter().stream()
                        .map(el -> modelMapper.map(el, SecteurResponse.class))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(secteurResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/afected")
    public ResponseEntity<List<SecteurResponse>> getSecteurAfected() {
        ModelMapper modelMapper = new ModelMapper();
        List<SecteurResponse> secteurResponses =
                secteurService.getSecteurAfected().stream()
                        .map(el -> modelMapper.map(el, SecteurResponse.class))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(secteurResponses, HttpStatus.OK);
    }
}
