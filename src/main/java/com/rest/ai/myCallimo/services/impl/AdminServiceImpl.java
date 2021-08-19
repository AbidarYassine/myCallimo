package com.rest.ai.myCallimo.services.impl;


import com.rest.ai.myCallimo.dao.AdminDao;
import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.AdminResponse;
import com.rest.ai.myCallimo.services.facade.AdminService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {


    private final AdminDao adminDao;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminDao = adminDao;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AdminDto save(AdminDto adminDto) {
        if (userService.findByEmail(adminDto.getEmail()) != null)
            throw new UserAlreadyExist("User already exists ");
        ModelMapper modelMapper = new ModelMapper();
        AdminEntity adminEntity = modelMapper.map(adminDto, AdminEntity.class);
        adminEntity.setPassword(bCryptPasswordEncoder.encode(adminDto.getPassword()));
        AdminEntity saved = adminDao.save(adminEntity);
        return modelMapper.map(saved, AdminDto.class);
    }

    @Override
    public List<AdminDto> findAll() {
        return null;
    }

    @Override
    public AdminDto findById(Integer id) {
        return null;
    }

    @Override
    public AdminResponse findByEmail(String email) {
        AdminEntity adminEntity = adminDao.findByEmail(email);
        if (adminEntity == null) throw new UserNotFoundException("admin non trouver !!");
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(adminEntity, AdminResponse.class);
    }
}
