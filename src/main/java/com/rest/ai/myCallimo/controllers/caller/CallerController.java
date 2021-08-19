package com.rest.ai.myCallimo.controllers.caller;


import com.rest.ai.myCallimo.dto.CallerDto;
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
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin/callers")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
public class CallerController {

    private final CallerService callerService;
    private final AuthRoleService roleService;
    private final ModelMapper modelMapper;

    public CallerController(CallerService callerService, AuthRoleService authRoleService, AuthRoleService roleService, ModelMapper modelMapper) {
        this.callerService = callerService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable() String email) {
        CallerDto callerDto = callerService.findByEmail(email);
        return new ResponseEntity<>(modelMapper.map(callerDto, CallerResponse.class), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllCallers() {
        return new ResponseEntity<>(callerService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/supervisor-id/{supervisor_id}")
    public ResponseEntity<CallerResponse> save(@Valid @RequestBody() CallerDto callerDto, @PathVariable() Integer supervisor_id) {
        CallerResponse allerResponse = callerService.save(callerDto, supervisor_id);
        return new ResponseEntity<>(allerResponse, HttpStatus.CREATED);

    }

//    @GetMapping("/supervisor-id/{id}")
//    public ResponseEntity<List<CallerResponse>> getBySupervisorId(@PathVariable("id") Integer id) {
//        List<CallerDto> callerDtos = callerService.getBySupervisorId(id);
//        if (callerDtos == null) throw new UserNotFoundException("superviseur non trouver par id " + id);
//
//        return new ResponseEntity<>(callerDtos.stream().map(el -> modelMapper.map(el, CallerResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
//    }

    @GetMapping("/user-callers")
    public ResponseEntity<List<CallerResponse>> getCallers() {
        List<CallerResponse> callerResponse = new ArrayList<>();

        return new ResponseEntity<>(roleService.getCallers(), HttpStatus.OK);
    }
}
