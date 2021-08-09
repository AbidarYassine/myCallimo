package com.rest.ai.myCallimo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin() {
        return "for admin";
    }

    @GetMapping("/sup")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String getSup() {
        return "for sup";
    }

    @GetMapping("/caller")
    @PreAuthorize("hasRole('CALLER')")
    public String getCaller() {
        return "for caller";
    }
}
