package com.rest.ai.myCallimo.services.impl;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AuthRoleServiceImpl implements AuthRoleService {

    private final UserService userService;

    public AuthRoleServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDto getUserAuth() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findByEmail(userName);
        return userDto;
    }

    @Override
    public String getRole() {
        return this.getUserAuth().getRole();
    }

    @Override
    public List<CallerResponse> getCallers() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findByEmail(userName);
        return this.userService.findCallersByUserId(userDto.getId());
    }
}
