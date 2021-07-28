package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.config.security.CustomUserDetails;
import com.rest.ai.myCallimo.config.security.jwt.JwtUtils;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.request.UserLoginRequest;
import com.rest.ai.myCallimo.response.JwtResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/users")
@RestController()
public class UserController {

    private final UserService userService;
    private final AuthRoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @Autowired
    public UserController(UserService userService, AuthRoleService roleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> save(@RequestBody() UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserDto userDto = userService.findByEmail(userDetails.getEmail());
        String role = userDto.getRole();
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(jwt)
                .id(userDto.getId())
                .type("Bearer")
                .role(role).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }

    @GetMapping()
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

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getAuthUser() {
        UserDto userDto = roleService.getUserAuth();
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<UserResponse>(modelMapper.map(userDto, UserResponse.class), HttpStatus.OK);
    }
}
