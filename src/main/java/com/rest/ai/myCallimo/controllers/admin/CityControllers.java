package com.rest.ai.myCallimo.controllers.admin;

import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.response.CityResponse;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.services.facade.CityService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/cities")
@CrossOrigin("*")
@Slf4j
public class CityControllers {
    private final CityService cityService;

    public CityControllers(CityService cityService) {
        this.cityService = cityService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public CityDto save(@Valid @RequestBody() CityDto cityDto) {
        return cityService.save(cityDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/")
    public ResponseEntity<List<CityResponse>> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<CityResponse> cities = cityService.findAll()
                .stream()
                .map(el -> modelMapper.map(el, CityResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/id/{id}")
    public CityDto findById(@PathVariable("id") Integer id) {
        CityDto cityDto = cityService.findById(id);
        if (cityDto == null) throw new CityNotFoundException("City not found by Id " + id);
        return cityDto;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/name/{name}")
    public CityDto findByName(@PathVariable("name") String name) {
        CityDto cityDto = cityService.findByName(name);
        if (cityDto == null) throw new CityNotFoundException("City not found by Id " + name);
        return cityDto;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/id/{id}")
    public int delete(@PathVariable("id") Integer id) {
        return cityService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/supervisor/city/{id}")
    public ResponseEntity<SupervisorResponse> getByCity(@PathVariable() Integer id) {
        SupervisorDto res = cityService.getByCity(id);
        if (res == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(res, SupervisorResponse.class), HttpStatus.OK);
    }
}



