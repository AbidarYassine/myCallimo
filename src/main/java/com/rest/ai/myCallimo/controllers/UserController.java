package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RequestMapping("users")
@RestController()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserResponse> save(@RequestBody() UserDto userDto) {
        UserDto userDto1 = null;
        if (userDto1 == null)
            throw new UserAlreadyExist("User with email " + userDto.getEmail() + " exists !!");
        ModelMapper modelMapper = new ModelMapper();
        UserResponse userResponse = modelMapper.map(userDto1, UserResponse.class);
        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    @Secured("ROLE_SUPER_ADMIN")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserDto> userDtos = userService.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        userDtos.forEach(el -> {
            userResponses.add(modelMapper.map(el, UserResponse.class));
        });
        return new ResponseEntity<List<UserResponse>>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {
        UserDto userDto = userService.findByEmail(email);
        if (userDto == null)
            throw new UserNotFoundException("User Not Found");
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<UserResponse>(modelMapper.map(userDto, UserResponse.class), HttpStatus.OK);
    }
}
