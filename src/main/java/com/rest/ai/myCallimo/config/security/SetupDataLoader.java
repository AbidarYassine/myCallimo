package com.rest.ai.myCallimo.config.security;


import com.rest.ai.myCallimo.dao.AdminDao;
import com.rest.ai.myCallimo.dao.RoleDao;

import com.rest.ai.myCallimo.entities.AdminEntity;

import com.rest.ai.myCallimo.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;


    private final AdminDao adminDao;


    private final RoleDao roleDao;

    private final Utils utils;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SetupDataLoader(AdminDao adminDao, RoleDao roleDao, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        System.out.println("executing SetupDataLoader ");
        this.adminDao = adminDao;
        this.utils = utils;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        if (adminDao.findByEmail("admin@gmail.com") != null) {
            return;
        }
        AdminEntity admin = new AdminEntity();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEncryptedPassword(bCryptPasswordEncoder.encode("admin1234"));
        admin.setEmail("admin@gmail.com");
        adminDao.save(admin);
        alreadySetup = true;
    }

}
