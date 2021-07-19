package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.entities.UserEntity;
import com.rest.ai.myCallimo.exception.role.RoleNotFoundException;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
public class AuthRoleServiceImpl implements AuthRoleService {

    private final UserService userService;

    public AuthRoleServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isAuthorized(String role) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.findByEmail(userName);
        log.info("Role is " + user.getRole());
        if (!user.getRole().equalsIgnoreCase(role))  // is role isn't match
            return false;
        return true;
    }

    @Override
    public UserDto getUserAuth() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByEmail(userName);

    }
}
