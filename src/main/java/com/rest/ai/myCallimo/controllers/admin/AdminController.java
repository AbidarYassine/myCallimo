package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin/supervisors")
@CrossOrigin("*")
@Slf4j
public class AdminController {
    private final AuthRoleService authRoleService;
    private final SupervisorService supervisorService;
    private final AdminService adminService;
    private final CityService cityService;


    @Autowired
    public AdminController(AuthRoleService authRoleService, SupervisorService supervisorService, AdminService adminService, CityService cityService) {
        this.authRoleService = authRoleService;
        this.supervisorService = supervisorService;
        this.adminService = adminService;
        this.cityService = cityService;
    }

    @PostMapping("/add-supervisor")
    public ResponseEntity<UserResponse> addSupervisor(@RequestBody() SupervisorDto supervisorDto) {
        this.isAuthorized();
        ModelMapper modelMapper = new ModelMapper();
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto result = supervisorService.save(supervisorDto, modelMapper.map(authUser, AdminDto.class));
        return new ResponseEntity<>(modelMapper.map(result, UserResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<SupervisorResponse>> getSupervisor() {
        isAuthorized();
        UserDto authUser = authRoleService.getUserAuth();
        AdminDto adminDto = adminService.findByEmail(authUser.getEmail());
        ModelMapper modelMapper = new ModelMapper();
        if (adminDto == null)
            throw new UserNotFoundException("Admin not found ");
        log.info("admin entity {}", adminDto);
        List<SupervisorResponse> userResponses = new ArrayList<>();
        adminDto.getSupervisors().forEach(el -> {
            userResponses.add(modelMapper.map(el, SupervisorResponse.class));
        });
        log.info("supervisors {}", adminDto.getSupervisors());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SupervisorResponse> findById(@PathVariable() Integer id) {
        isAuthorized();
        SupervisorDto supervisorDto = supervisorService.findById(id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, SupervisorResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/delete-supervisor/{id}")
    public int deleteSupervisor(@PathVariable() Integer id) {
        isAuthorized();
        supervisorService.deleteById(id);
        return 1;
    }


    @PutMapping("/update-supervisor/{id}")
    public ResponseEntity<UserResponse> updateSupervisor(@RequestBody() SupervisorDto supervisorDto, @PathVariable() Integer id) {
        isAuthorized();
        int res = supervisorService.update(supervisorDto, id);
        if (res == -1) throw new UserNotFoundException("supervisor not found");
        if (res == -2) throw new UserAlreadyExist("User with email " + supervisorDto.getEmail() + " already exists");
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, UserResponse.class), HttpStatus.CREATED);
    }


    private void isAuthorized() {
        if (!authRoleService.isAuthorized("ADMIN"))
            throw new UnAuthorizationUser("Access Denied");
    }

}

