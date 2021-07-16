package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.dto.RoleDto;
import com.rest.ai.myCallimo.exception.role.RoleAlreadyExistsException;
import com.rest.ai.myCallimo.exception.role.RoleNotFoundException;
import com.rest.ai.myCallimo.response.RoleResponse;
import com.rest.ai.myCallimo.services.facade.RoleService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("roles")
@RestController()
public class RoleController {
    private final RoleService roleService;
    // TODO Spring Batch Tutorial

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<RoleResponse> findByName(@PathVariable() String role) throws NotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        RoleDto roleDto = roleService.findByName(role);
        if (roleDto == null)
            throw new RoleNotFoundException("Role " + role + " not found");
        RoleResponse roleResponse = modelMapper.map(roleDto, RoleResponse.class);
        return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RoleResponse> save(@RequestBody() RoleDto roleDto) {
        RoleDto role = roleService.save(roleDto);
        if (role == null)
            throw new RoleAlreadyExistsException("Role " + roleDto.getName() + " was created !!");
        ModelMapper modelMapper = new ModelMapper();
        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);
        return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.CREATED);
    }
}
