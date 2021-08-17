package com.rest.ai.myCallimo.controllers;

import com.flickr4java.flickr.FlickrException;
import com.rest.ai.myCallimo.config.security.CustomUserDetails;
import com.rest.ai.myCallimo.config.security.jwt.JwtUtils;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.request.ChangePasswordRequest;
import com.rest.ai.myCallimo.request.UserLoginRequest;
import com.rest.ai.myCallimo.response.JwtResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.FlickrService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RequestMapping("/users")
@RestController()
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthRoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final FlickrService flickrService;
    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, AuthRoleService roleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, FlickrService flickrService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.flickrService = flickrService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> save(@Valid @RequestBody() UserLoginRequest userLoginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserDto userDto = userService.findByEmail(userDetails.getEmail());

        String role = userDto.getRole();
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(jwt)
                .user(modelMapper.map(userDto, UserResponse.class))
                .type("Bearer")
                .build();
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

    @CrossOrigin(origins = "*")
    @GetMapping("/info")
    public ResponseEntity<UserResponse> getAuthUser() {
        UserDto userDto = roleService.getUserAuth();
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<UserResponse>(modelMapper.map(userDto, UserResponse.class), HttpStatus.OK);
    }

    @PostMapping("/modifer-mot-passe")
    public UserResponse changePassword(@Valid @RequestBody() ChangePasswordRequest changePasswordRequest) {
        UserDto userDto = userService.changePassword(changePasswordRequest);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, UserResponse.class);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/modifier-avatar/{email}")
    public String modifierAvatar(@RequestPart("file") MultipartFile file, @PathVariable() String email) throws IOException, FlickrException, ExecutionException, InterruptedException {
        return flickrService.savePhoto(file.getInputStream(), email);
    }
}
