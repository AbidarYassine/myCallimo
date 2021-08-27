package com.rest.ai.myCallimo.controllers.caller;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CallerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin/callers")
@CrossOrigin("*")

public class CallerController {

    private final CallerService callerService;
    private final AuthRoleService roleService;
    private final ModelMapper modelMapper;

    public CallerController(CallerService callerService, AuthRoleService authRoleService, AuthRoleService roleService, ModelMapper modelMapper) {
        this.callerService = callerService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable() String email) {
        CallerDto callerDto = callerService.findByEmail(email);
        return new ResponseEntity<>(modelMapper.map(callerDto, CallerResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllCallers() {
        return new ResponseEntity<>(callerService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @PostMapping("/supervisor-id/{supervisor_id}")
    public ResponseEntity<CallerResponse> save(@Valid @RequestBody() CallerDto callerDto, @PathVariable() Integer supervisor_id) {
        CallerResponse allerResponse = callerService.save(callerDto, supervisor_id);
        return new ResponseEntity<>(allerResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/user-callers")
    public ResponseEntity<List<CallerResponse>> getCallers() {
        return new ResponseEntity<>(roleService.getCallers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/appeles/id/{id}")
    public List<AppelResponse> getAppeles(@PathVariable() Integer id) {
        return callerService.getAppeles(id);
    }
}
