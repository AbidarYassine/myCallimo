package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.UserDao;
import com.rest.ai.myCallimo.dto.RoleDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.RoleEntity;
import com.rest.ai.myCallimo.entities.UserEntity;
import com.rest.ai.myCallimo.exception.role.RoleNotFoundException;
import com.rest.ai.myCallimo.services.facade.RoleService;
import com.rest.ai.myCallimo.services.facade.UserService;
import com.rest.ai.myCallimo.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Utils utils;

    public UserServiceImpl(UserDao userDao, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, Utils utils) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.utils = utils;
    }

    @Override
    public UserDto save(UserDto userDto, String role) {
        ModelMapper modelMapper = new ModelMapper();
        if (userDao.findByEmail(userDto.getEmail()) != null)
            return null;
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setUserId(utils.generateUserId(12));
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        RoleDto roleDto = roleService.findByName(role);
        if (roleDto == null)
            throw new RoleNotFoundException("Role not found");
        RoleEntity roleS = modelMapper.map(roleDto, RoleEntity.class);
        user.setRole(roleS);
        UserEntity userEntity = userDao.save(user);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<UserEntity> users = userDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(el -> {
            UserDto user = modelMapper.map(el, UserDto.class);
            userDtos.add(user);
        });
        return userDtos;
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity userEntity = userDao.findByEmail(email);
        if (userEntity == null)
            return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UserDto.class);
    }
}
