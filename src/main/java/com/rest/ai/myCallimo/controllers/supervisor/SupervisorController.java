package com.rest.ai.myCallimo.controllers.supervisor;

import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/supervisor")
public class SupervisorController {


    private final AuthRoleService authRoleService;

    @Autowired
    public SupervisorController(AuthRoleService authRoleService) {
        this.authRoleService = authRoleService;
    }

    @GetMapping()
    public String getRes() {
        if (!authRoleService.isAuthorized("SUPERVISOR"))
            throw new UnAuthorizationUser("Access Denied");
        return "for supervisor";
    }
}
