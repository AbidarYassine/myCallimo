package com.rest.ai.myCallimo.controllers.supervisor;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    private final ModelMapper modelMapper;

    @Autowired
    public SupervisorController(AuthRoleService authRoleService, CallerService callerService, SupervisorService supervisorService, ModelMapper modelMapper) {
        this.authRoleService = authRoleService;
        this.callerService = callerService;
        this.supervisorService = supervisorService;
        this.modelMapper = modelMapper;
    }


    //    supervisor can add caller
    @PostMapping("/add-callers")
    public ResponseEntity<CallerResponse> addCaller(@Valid @RequestBody() CallerDto callerDto) {
        UserDto authUser = authRoleService.getUserAuth();
        CallerResponse result = callerService.save(callerDto, authUser.getId());
        return new ResponseEntity<>(modelMapper.map(result, CallerResponse.class), HttpStatus.CREATED);
    }


    //    get callers of supervisor
    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/callers")
    public ResponseEntity<List<UserResponse>> getCallers() {
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto supervisorDto = supervisorService.findByEmail(authUser.getEmail());

        List<UserResponse> userResponses = new ArrayList<>();
        supervisorDto.getCallers().forEach(el -> {
            userResponses.add(modelMapper.map(el, UserResponse.class));
        });
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/offres")
    public ResponseEntity<List<OffreDto>> getOffre() {
        UserDto authUser = authRoleService.getUserAuth();
        if (authUser.getRole().equals("SUPERVISOR")) {
            SupervisorDto supervisorDto = supervisorService.findByEmail(authUser.getEmail());
            return new ResponseEntity<>(supervisorDto.getOffres(), HttpStatus.OK);
        } else {
            throw new AccessDeniedException("Access denied !!");
        }
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/appeles/id/{id}")
    public List<AppelResponse> getAppeles(@PathVariable() Integer id) {
        return supervisorService.getAppeles(id);
    }
}
