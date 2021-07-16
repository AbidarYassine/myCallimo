package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.entities.UserEntity;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    private final UserService userService;

    public AuthRoleServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isAuthorized(String role) {
        System.out.println(role);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("user " + userName);
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userService.findByEmail(userName), UserEntity.class);
        System.out.println("role " + userEntity.getRole().getName());
        if (!userEntity.getRole().getName().equalsIgnoreCase("ADMIN"))
            return false;
        return true;
    }
}
