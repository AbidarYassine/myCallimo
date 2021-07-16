package com.rest.ai.myCallimo.config.security;


import com.rest.ai.myCallimo.dao.RoleDao;
import com.rest.ai.myCallimo.dao.UserDao;
import com.rest.ai.myCallimo.entities.CategoryEntity;
import com.rest.ai.myCallimo.entities.RoleEntity;
import com.rest.ai.myCallimo.entities.UserEntity;
import com.rest.ai.myCallimo.services.facade.CategoryService;
import com.rest.ai.myCallimo.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;


    private final UserDao userDao;


    private final RoleDao roleDao;

    private final Utils utils;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SetupDataLoader(UserDao userDao, RoleDao roleDao, Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        System.out.println("executing SetupDataLoader ");
        this.userDao = userDao;
        this.utils = utils;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("SUPERVISOR");
        createRoleIfNotFound("CALLER");

        RoleEntity adminRole = roleDao.findByName("ADMIN");
        UserEntity user = new UserEntity();
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setEncryptedPassword(bCryptPasswordEncoder.encode("admin1234"));
        user.setEmail("admin@gmail.com");
        user.setUserId(utils.generateUserId(12));
        user.setRole(adminRole);
//        userDao.save(user);
        alreadySetup = true;
    }

    @Transactional
    RoleEntity createRoleIfNotFound(String name) {
        RoleEntity role = roleDao.findByName(name);
        if (role == null) {
            role = new RoleEntity();
            role.setName(name);
            roleDao.save(role);
        }
        return role;
    }
}
