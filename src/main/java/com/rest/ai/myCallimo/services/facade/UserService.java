package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.UserDto;


import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto, String role);

    List<UserDto> findAll();


    UserDto findByEmail(String email);


}
