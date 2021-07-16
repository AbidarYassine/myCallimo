package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AuthRoleService authRoleService;

    @Autowired
    public AdminController(AuthRoleService authRoleService) {
        this.authRoleService = authRoleService;
    }

    @GetMapping()
    public String add() {
        if (!authRoleService.isAuthorized("ADMIN"))
            throw new UnAuthorizationUser("Access Denied");
        return "for and admin";
    }

}

