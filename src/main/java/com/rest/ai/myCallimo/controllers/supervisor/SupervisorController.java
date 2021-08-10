package com.rest.ai.myCallimo.controllers.supervisor;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.SecteurResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/supervisor")
@CrossOrigin("*")
@Slf4j
public class SupervisorController {


    private final AuthRoleService authRoleService;
    private final CallerService callerService;
    private final SupervisorService supervisorService;


    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/affecter/{sup_id}/{secteur_id}")
    public ResponseEntity<SecteurResponse> affecterSupToSecteur(@PathVariable() Integer sup_id, @PathVariable() Integer secteur_id) {
        SecteurDto dto = supervisorService.affecterSupToSecteur(sup_id, secteur_id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @Autowired
    public SupervisorController(AuthRoleService authRoleService, CallerService callerService, SupervisorService supervisorService) {
        this.authRoleService = authRoleService;
        this.callerService = callerService;
        this.supervisorService = supervisorService;
    }

    //    supervisor can add caller
    @PostMapping("/add-callers")
    public ResponseEntity<CallerResponse> addCaller(@Valid @RequestBody() CallerDto callerDto) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto authUser = authRoleService.getUserAuth();
        CallerDto result = callerService.save(callerDto, authUser.getId());
        return new ResponseEntity<>(modelMapper.map(result, CallerResponse.class), HttpStatus.CREATED);
    }


    //    get callers of supervisor
    @GetMapping("/callers")
    public ResponseEntity<List<UserResponse>> getCallers() {
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto supervisorDto = supervisorService.findByEmail(authUser.getEmail());
        ModelMapper modelMapper = new ModelMapper();
        List<UserResponse> userResponses = new ArrayList<>();
        supervisorDto.getCallers().forEach(el -> {
            userResponses.add(modelMapper.map(el, UserResponse.class));
        });
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }


}
