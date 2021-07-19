package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.UserDto;


import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto, int role, UserDto authUser);

//    role 0==> callers, 1==> Supervisor  ,2==>Admin

    List<UserDto> findAll();


    UserDto findByEmail(String email);


}
