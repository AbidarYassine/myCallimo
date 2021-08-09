package com.rest.ai.myCallimo.controllers.admin;

import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.response.CityResponse;
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
@PreAuthorize("hasRole('ADMIN')")
public class CityControllers {
    private final CityService cityService;

    public CityControllers(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/")
    public CityDto save(@Valid @RequestBody() CityDto cityDto) {
        return cityService.save(cityDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<CityResponse>> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<CityResponse> cities = cityService.findAll()
                .stream()
                .map(el -> modelMapper.map(el, CityResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public CityDto findById(@PathVariable("id") Integer id) {
        CityDto cityDto = cityService.findById(id);
        if (cityDto == null) throw new CityNotFoundException("City not found by Id " + id);
        return cityDto;
    }

    @GetMapping("/name/{name}")
    public CityDto findByName(@PathVariable("name") String name) {
        CityDto cityDto = cityService.findByName(name);
        if (cityDto == null) throw new CityNotFoundException("City not found by Id " + name);
        return cityDto;
    }

    @DeleteMapping("/id/{id}")
    public int delete(@PathVariable("id") Integer id) {
        return cityService.delete(id);
    }

}



