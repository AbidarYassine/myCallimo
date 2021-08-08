package com.rest.ai.myCallimo.controllers.caller;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin/callers")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
public class CallerController {

    private final CallerService callerService;

    public CallerController(CallerService callerService, AuthRoleService authRoleService) {
        this.callerService = callerService;
    }


    @GetMapping("email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable() String email) {
        CallerDto callerDto = callerService.findByEmail(email);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(callerDto, CallerResponse.class), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllCallers() {
        ModelMapper modelMapper = new ModelMapper();
        List<CallerDto> callerDtos = callerService.getAll();
        List<UserResponse> userResponses = callerDtos.stream().map(el -> modelMapper.map(el, UserResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PostMapping("/supervisor-id/{supervisor_id}")
    public ResponseEntity<CallerResponse> save(@Valid @RequestBody() CallerDto callerDto, @PathVariable() Integer supervisor_id) {
        CallerDto callerDtoResp = callerService.save(callerDto, supervisor_id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(callerDtoResp, CallerResponse.class), HttpStatus.CREATED);

    }

    @GetMapping("/supervisor-id/{id}")
    public ResponseEntity<List<CallerResponse>> getBySupervisorId(@PathVariable("id") Integer id) {
        List<CallerDto> callerDtos = callerService.getBySupervisorId(id);
        if (callerDtos == null) throw new UserNotFoundException("superviseur non trouver par id " + id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(callerDtos.stream().map(el -> modelMapper.map(el, CallerResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }


}
