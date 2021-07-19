package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AdminService;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final AuthRoleService authRoleService;
    private final SupervisorService supervisorService;
    private final AdminService adminService;

    @Autowired
    public AdminController(AuthRoleService authRoleService, SupervisorService supervisorService, AdminService adminService) {
        this.authRoleService = authRoleService;
        this.supervisorService = supervisorService;
        this.adminService = adminService;
    }

    @PostMapping("/add-supervisor")
    public ResponseEntity<UserResponse> addSupervisor(@RequestBody() SupervisorDto supervisorDto) {
        this.isAuthorized();
        ModelMapper modelMapper = new ModelMapper();
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto result = supervisorService.save(supervisorDto, modelMapper.map(authUser, AdminDto.class));
        return new ResponseEntity<>(modelMapper.map(result, UserResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("supervisors")
    public ResponseEntity<List<UserResponse>> getSupervisor() {
        isAuthorized();
        UserDto authUser = authRoleService.getUserAuth();
        AdminDto adminDto = adminService.findByEmail(authUser.getEmail());
        ModelMapper modelMapper = new ModelMapper();
        if (adminDto == null)
            throw new UserNotFoundException("Admin not found ");
        log.info("admin entity {}", adminDto);
        List<UserResponse> userResponses = new ArrayList<>();
        adminDto.getSupervisors().forEach(el -> {
            userResponses.add(modelMapper.map(el, UserResponse.class));
        });
        log.info("supervisors {}", adminDto.getSupervisors());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    private void isAuthorized() {
        if (!authRoleService.isAuthorized("ADMIN"))
            throw new UnAuthorizationUser("Access Denied");
    }

}

