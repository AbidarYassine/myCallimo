package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.AdminDao;
import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dao.SupervisorDao;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.role.RoleNotFoundException;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final AdminDao adminDao;

    private final SupervisorDao supervisorDao;
    private final CallerDao callerDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(AdminDao adminDao, SupervisorDao supervisorDao, CallerDao callerDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminDao = adminDao;
        this.supervisorDao = supervisorDao;
        this.callerDao = callerDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

//    @Override
//    public UserDto save(UserDto userDto, String role) {
//        ModelMapper modelMapper = new ModelMapper();
//        if (adminDao.findByEmail(userDto.getEmail()) != null)
//            return null;
//        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
//        userDto.setUserId(utils.generateUserId(12));
//        UserEntity user = modelMapper.map(userDto, UserEntity.class);
//        RoleDto roleDto = roleService.findByName(role);
//        if (roleDto == null)
//            throw new RoleNotFoundException("Role not found");
//        RoleEntity roleS = modelMapper.map(roleDto, RoleEntity.class);
//        user.setRole(roleS);
//        UserEntity userEntity = adminDao.save(user);
//        return modelMapper.map(userEntity, UserDto.class);
//    }

    @Override
    public UserDto addUser(UserDto userDto, int role, UserDto authUser) {
        UserDto user = findByEmail(userDto.getEmail());
        ModelMapper modelMapper = new ModelMapper();
        if (user != null) return null;
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        switch (role) {
            case 0:
                userDto.setRole("CALLER");
                CallerEntity callerEntity = modelMapper.map(userDto, CallerEntity.class);
                callerDao.save(callerEntity);
                return userDto;
            case 1:
                userDto.setRole("SUPERVISOR");
                SupervisorEntity supervisorEntity = modelMapper.map(userDto, SupervisorEntity.class);
                supervisorDao.save(supervisorEntity);
                return userDto;
            case 3:
                userDto.setRole("ADMIN");
                AdminEntity adminEntity = modelMapper.map(userDto, AdminEntity.class);
                adminDao.save(adminEntity);
                return userDto;
            default:
                throw new RoleNotFoundException("Role is not defined");
        }

    }

    @Override
    public List<UserDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<AdminEntity> users = adminDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(el -> {
            UserDto user = modelMapper.map(el, UserDto.class);
            userDtos.add(user);
        });
        return userDtos;
    }

    @Override
    public UserDto findByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        AdminEntity adminEntity = adminDao.findByEmail(email);
        if (adminEntity != null) {
            return modelMapper.map(adminEntity, UserDto.class);
        }
        CallerEntity callerEntity = callerDao.findByEmail(email);
        if (callerEntity != null) {
            return modelMapper.map(callerEntity, UserDto.class);
        }
        SupervisorEntity supervisorEntity = supervisorDao.findByEmail(email);
        if (supervisorEntity != null) {
            return modelMapper.map(supervisorEntity, UserDto.class);
        }
        return null;
    }
}
