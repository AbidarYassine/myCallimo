package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.services.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private EmailSenderService emailSenderService;

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


    @GetMapping("/send-email/{email}")
    public void sendEmail(@PathVariable String email) {
//        emailSenderService.sendSimpleEmail(email, "test", "test");
    }
}
