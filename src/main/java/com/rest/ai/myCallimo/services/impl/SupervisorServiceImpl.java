package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.SupervisorDao;
import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.CityService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
@Slf4j
public class SupervisorServiceImpl implements SupervisorService {


    private final SupervisorDao supervisorDao;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CityService cityService;

    @Autowired
    public SupervisorServiceImpl(SupervisorDao supervisorDao, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, CityService cityService) {
        this.supervisorDao = supervisorDao;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.cityService = cityService;
    }


    @Override
    public SupervisorDto findByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        if (email.isEmpty() || email.isBlank()) {
            return null;
        }
        SupervisorEntity supervisorEntity = supervisorDao.findByEmail(email);
        return modelMapper.map(supervisorEntity, SupervisorDto.class);
    }

    @Transactional
    @Override
    public int delete(Integer id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).get();
        supervisorDao.delete(supervisorEntity);
        log.info("wsal hna ...");
        return 1;

    }

    @Override
    public SupervisorDto findById(Integer id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).orElse(null);
        if (supervisorEntity == null) throw new UserNotFoundException("Superviseur non trouver par l'id " + id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(supervisorEntity, SupervisorDto.class);
    }

    @Override
    public int update(SupervisorDto supervisorDto, Integer id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).orElse(null);
        if (supervisorEntity == null) return -1;
        if (userService.findByEmail(supervisorDto.getEmail()) != null && !supervisorEntity.getEmail().equals(supervisorDto.getEmail()))
            return -2;
        ModelMapper modelMapper = new ModelMapper();
        String password;
        if (supervisorDto.getPassword() == null || supervisorDto.getPassword().isEmpty()) {
            password = supervisorEntity.getPassword();
        } else {
            password = bCryptPasswordEncoder.encode(supervisorDto.getPassword());
        }
        supervisorDto.setId(supervisorEntity.getId());
        supervisorEntity = modelMapper.map(supervisorDto, SupervisorEntity.class);
        supervisorEntity.setPassword(password);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AdminEntity adminEntity = modelMapper.map(userService.findByEmail(userName), AdminEntity.class);
        supervisorEntity.setAdmin(adminEntity);
        supervisorDao.save(supervisorEntity);
        return 1;
    }

    @Override
    public void deleteAllByEmail(String email) {
//        SupervisorEntity supervisorEntity = supervisorDao.findByEmail(email);
        supervisorDao.deleteAllByEmail(email);
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).orElse(null);
        if (supervisorEntity == null) throw new UserNotFoundException("Superviseur non trouver par l'id " + id);
        supervisorEntity.setCallers(new ArrayList<>());
        supervisorDao.save(supervisorEntity);
        supervisorDao.deleteById(id);
        return 1;
    }


    @Override
    public SupervisorDto save(SupervisorDto t, AdminDto admin) {
        if (userService.findByEmail(t.getEmail()) != null) {
            throw new UserAlreadyExist("user with this email " + t.getEmail() + " already exists");
        }
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(t, SupervisorEntity.class);
        supervisorEntity.setRole("SUPERVISOR");
        AdminEntity adminEntity = modelMapper.map(admin, AdminEntity.class);
        supervisorEntity.setAdmin(adminEntity);
        supervisorEntity.setPassword(bCryptPasswordEncoder.encode(t.getPassword()));
        SupervisorEntity supervisor = supervisorDao.save(supervisorEntity);
        return modelMapper.map(supervisor, SupervisorDto.class);
    }

    @Override
    public SupervisorDto save(SupervisorDto t) {
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(t, SupervisorEntity.class);
        SupervisorEntity saved = supervisorDao.save(supervisorEntity);
        return modelMapper.map(saved, SupervisorDto.class);
    }
}
