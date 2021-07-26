package com.rest.ai.myCallimo.controllers.caller;

import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/caller")
@CrossOrigin("*")
public class CallerController {

    private final AuthRoleService authRoleService;

    @Autowired
    public CallerController(AuthRoleService authRoleService) {
        this.authRoleService = authRoleService;
    }

    @GetMapping()
    public String getRes() {
        if (!authRoleService.isAuthorized("CALLER"))
            throw new UnAuthorizationUser("Access Denied");
        return "for Callers";
    }
}
