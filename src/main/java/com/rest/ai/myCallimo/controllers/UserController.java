package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
@RestController()
public class UserController {

    private final UserService userService;
    private final AuthRoleService roleService;


    @Autowired
    public UserController(UserService userService, AuthRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping()
    public ResponseEntity<UserResponse> save(@RequestBody() UserDto userDto) {
        UserDto userDto1 = userService.findByEmail(userDto.getEmail());
        if (userDto1 == null)
            throw new UserAlreadyExist("User with email " + userDto.getEmail() + " exists !!");
        ModelMapper modelMapper = new ModelMapper();
        UserResponse userResponse = modelMapper.map(userDto1, UserResponse.class);
        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
    }

//    @PostMapping()
//    public ResponseEntity<UserResponse> login(@RequestBody() UserLoginRequest userLoginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword(), new ArrayList<>()));
//        String userName = ((User) authentication.getPrincipal()).getUsername();
//        String token = Jwts.builder()
//                .setSubject(userName)
//                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SECRET)
//                .compact();
//
//
//        res.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
//
//    }

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
