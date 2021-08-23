package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.services.facade.AppelService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/appel")
@RestController()
public class AppelController {

    private final AppelService appelService;

    public AppelController(AppelService appelService) {
        this.appelService = appelService;
    }

    @PostMapping("")
    public AppelResponse save(@Valid @RequestBody() AppelDto appelDto) {
        System.out.println(appelDto.getDate());
        return appelService.save(appelDto);
    }

    @PostMapping("/caller-id/{id}")
    public AppelResponse saveWithCaller(@RequestBody() AppelDto appelDto, @PathVariable() Integer id) {
        return appelService.saveWithCaller(appelDto, id);
    }

    @PostMapping("/supervisor-id/{id}")
    public AppelResponse saveWithSupervisor(@RequestBody() AppelDto appelDto, @PathVariable() Integer id) {
        return appelService.saveWithSupervisor(appelDto, id);
    }

    @DeleteMapping("/id/{id}")
    public int delete(@PathVariable Integer id) {
        return appelService.delete(id);
    }

    @GetMapping()
    public List<AppelResponse> findAll() {
        return appelService.findAll();
    }
}
