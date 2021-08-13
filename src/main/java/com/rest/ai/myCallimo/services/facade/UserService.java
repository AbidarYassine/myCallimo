package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.ChangePasswordRequest;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto, int role, UserDto authUser);

//    role 0==> callers, 1==> Supervisor  ,2==>Admin

    List<UserDto> findAll();


    UserDto findByEmail(String email);

    List<OffreDto> findByUserId(Integer id);


    UserDto changePassword(ChangePasswordRequest changePasswordRequest);


}
