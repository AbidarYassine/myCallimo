package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AdminService;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CityService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/admin/supervisors")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AuthRoleService authRoleService;
    private final SupervisorService supervisorService;
    private final AdminService adminService;
    private final CityService cityService;


    //    injection
    @Autowired
    public AdminController(AuthRoleService authRoleService, SupervisorService supervisorService, AdminService adminService, CityService cityService) {
        this.authRoleService = authRoleService;
        this.supervisorService = supervisorService;
        this.adminService = adminService;
        this.cityService = cityService;
    }


    // add supervisor by admin connected
    @PostMapping("/add-supervisor")
    public ResponseEntity<UserResponse> addSupervisor(@RequestBody() SupervisorDto supervisorDto) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto authUser = authRoleService.getUserAuth();
        SupervisorDto result = supervisorService.save(supervisorDto, modelMapper.map(authUser, AdminDto.class));
        return new ResponseEntity<>(modelMapper.map(result, UserResponse.class), HttpStatus.CREATED);
    }

    //    all supervisor  of admin connected
    @GetMapping("")
    public ResponseEntity<List<SupervisorResponse>> getSupervisor() {
        UserDto authUser = authRoleService.getUserAuth();
        AdminDto adminDto = adminService.findByEmail(authUser.getEmail());
        ModelMapper modelMapper = new ModelMapper();
        if (adminDto == null)
            throw new UserNotFoundException("Admin not found ");
        List<SupervisorResponse> userResponses = new ArrayList<>();
        adminDto.getSupervisors().forEach(el -> {
            userResponses.add(modelMapper.map(el, SupervisorResponse.class));
        });
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    //    find by id;
    @GetMapping("/id/{id}")
    public ResponseEntity<SupervisorResponse> findById(@PathVariable() Integer id) {
        SupervisorDto supervisorDto = supervisorService.findById(id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, SupervisorResponse.class), HttpStatus.OK);
    }

    //    TODO NOT WORK delete supervisor
    @DeleteMapping("/delete-supervisor/{id}")
    public int deleteSupervisor(@PathVariable() Integer id) {
        supervisorService.deleteById(id);
        return 1;
    }

    //    update supervisor done 80% add update city
    @PutMapping("/update-supervisor/{id}")
    public ResponseEntity<UserResponse> updateSupervisor(@RequestBody() SupervisorDto supervisorDto, @PathVariable() Integer id) {
        int res = supervisorService.update(supervisorDto, id);
        if (res == -1) throw new UserNotFoundException("supervisor not found");
        if (res == -2) throw new UserAlreadyExist("User with email " + supervisorDto.getEmail() + " already exists");
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, UserResponse.class), HttpStatus.CREATED);
    }


}

