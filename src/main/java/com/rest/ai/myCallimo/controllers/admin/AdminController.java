package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.AdminResponse;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AdminService;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin/supervisors")
@CrossOrigin("*")
@Slf4j
public class AdminController {
    private final AuthRoleService authRoleService;
    private final SupervisorService supervisorService;
    private final AdminService adminService;
    private final ModelMapper modelMapper;


    //    injection
    @Autowired
    public AdminController(AuthRoleService authRoleService, SupervisorService supervisorService, AdminService adminService, ModelMapper modelMapper) {
        this.authRoleService = authRoleService;
        this.supervisorService = supervisorService;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }


    // add supervisor by admin connected
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-supervisor")
    public ResponseEntity<SupervisorResponse> addSupervisor(@Valid @RequestBody() SupervisorDto supervisorDto) {
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto result = supervisorService.save(supervisorDto, modelMapper.map(authUser, AdminDto.class));
        return new ResponseEntity<>(modelMapper.map(result, SupervisorResponse.class), HttpStatus.CREATED);
    }

    //    all supervisor  of admin connected
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<SupervisorResponse>> getSupervisor() {
        UserDto authUser = authRoleService.getUserAuth();
        AdminResponse adminResponse = adminService.findByEmail(authUser.getEmail());
        return new ResponseEntity<>(adminResponse.getSupervisors(), HttpStatus.OK);
    }

    //    find by id;
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/id/{id}")
    public ResponseEntity<SupervisorResponse> findById(@PathVariable() Integer id) {
        return new ResponseEntity<>(supervisorService.findById(id), HttpStatus.OK);
    }

    //TODO NOT WORK delete supervisor
    @DeleteMapping("/delete-supervisor/{id}")
    public int deleteSupervisor(@PathVariable() Integer id) {
        return supervisorService.deleteById(id);
    }

    //    update supervisor done 80% add update city
    @PutMapping("/update-supervisor/{id}")
    public ResponseEntity<UserResponse> updateSupervisor(@Valid @RequestBody() SupervisorDto supervisorDto, @PathVariable() Integer id) {
        int res = supervisorService.update(supervisorDto, id);
        if (res == -1) throw new UserNotFoundException("supervisor not found");
        if (res == -2) throw new UserAlreadyExist("User with email " + supervisorDto.getEmail() + " already exists");
        return new ResponseEntity<>(modelMapper.map(supervisorDto, UserResponse.class), HttpStatus.CREATED);
    }


}

