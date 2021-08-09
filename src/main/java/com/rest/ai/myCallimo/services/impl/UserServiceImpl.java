package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.AdminDao;
import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dao.SupervisorDao;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.InvalidOperationException;
import com.rest.ai.myCallimo.exception.role.RoleNotFoundException;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.request.ChangePasswordRequest;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final AdminDao adminDao;
    private final SupervisorDao supervisorDao;
    private final CallerDao callerDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
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
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
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
            UserDto userDto = new UserDto();
            userDto.setFirstName(supervisorEntity.getFirstName());
            userDto.setLastName(supervisorEntity.getLastName());
            userDto.setEmail(supervisorEntity.getEmail());
            userDto.setPasswordChanged(supervisorEntity.isPasswordChanged());
            userDto.setPassword(supervisorEntity.getPassword());
            userDto.setRole(supervisorEntity.getRole());
            userDto.setTelephone(supervisorEntity.getTelephone());
            return userDto;
        }
        return null;
    }


    @Override
    public UserDto changePassword(ChangePasswordRequest changePasswordRequest) {
        UserDto userDto = this.findByEmail(changePasswordRequest.getEmail());
        if (userDto == null)
            throw new UserNotFoundException("Utilisateur non trouver par l'email " + changePasswordRequest.getEmail());
        if (!changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirmPassword()))
            throw new InvalidOperationException("le mot de passe et la confirmation sont different !!");
        String role = userDto.getRole();
        ModelMapper modelMapper = new ModelMapper();
        switch (role) {
            case "ADMIN":
                AdminEntity adminEntity = adminDao.findByEmail(changePasswordRequest.getEmail());
                log.info("admin entity {}", adminEntity);
                adminEntity.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getPassword()));
                log.info("admin entity {}", adminEntity);
                adminEntity.setPasswordChanged(true);
                AdminEntity adminSaved = adminDao.save(adminEntity);
                return modelMapper.map(adminSaved, UserDto.class);
            case "SUPERVISOR":
                SupervisorEntity supervisorEntity = supervisorDao.findByEmail(changePasswordRequest.getEmail());
                supervisorEntity.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getPassword()));
                supervisorEntity.setPasswordChanged(true);
                SupervisorEntity saved = supervisorDao.save(supervisorEntity);
                return modelMapper.map(saved, UserDto.class);
            case "CALLER":
                CallerEntity callerEntity = callerDao.findByEmail(changePasswordRequest.getEmail());
                callerEntity.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getPassword()));
                callerEntity.setPasswordChanged(true);
                CallerEntity callerSaved = callerDao.save(callerEntity);
                return modelMapper.map(callerSaved, UserDto.class);
            default:
                return null;
        }
    }
}
