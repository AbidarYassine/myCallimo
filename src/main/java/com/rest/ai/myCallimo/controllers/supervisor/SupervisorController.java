package com.rest.ai.myCallimo.controllers.supervisor;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/supervisor")
public class SupervisorController {


    private final AuthRoleService authRoleService;
    private final CallerService callerService;
    private final SupervisorService supervisorService;

    @Autowired
    public SupervisorController(AuthRoleService authRoleService, CallerService callerService, SupervisorService supervisorService) {
        this.authRoleService = authRoleService;
        this.callerService = callerService;
        this.supervisorService = supervisorService;
    }

    @PostMapping("/add-callers")
    public ResponseEntity<UserResponse> addCaller(@RequestBody() CallerDto callerDto) {
        isAuthorized();
        ModelMapper modelMapper = new ModelMapper();
        UserDto authUser = authRoleService.getUserAuth();
        CallerDto result = callerService.save(callerDto, modelMapper.map(authUser, SupervisorDto.class));
        return new ResponseEntity<>(modelMapper.map(result, UserResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/callers")
    public ResponseEntity<List<UserResponse>> getCallers() {
        isAuthorized();
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto supervisorDto = supervisorService.findByEmail(authUser.getEmail());
        ModelMapper modelMapper = new ModelMapper();
        List<UserResponse> userResponses = new ArrayList<>();
        supervisorDto.getCallers().forEach(el -> {
            userResponses.add(modelMapper.map(el, UserResponse.class));
        });
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    private void isAuthorized() {
        if (!authRoleService.isAuthorized("SUPERVISOR"))
            throw new UnAuthorizationUser("Access Denied");
    }
}
