package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CallerServiceImpl implements CallerService {


    private final CallerDao callerDao;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CallerServiceImpl(CallerDao callerDao, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.callerDao = callerDao;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CallerDto findByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        CallerEntity callerEntity = callerDao.findByEmail(email);
        return modelMapper.map(callerEntity, CallerDto.class);
    }

    @Override
    public CallerDto save(CallerDto callerDto, SupervisorDto supervisor) {
        if (userService.findByEmail(callerDto.getEmail()) != null) {
            throw new UserAlreadyExist("user with this email " + callerDto.getEmail() + " already exists");
        }
        ModelMapper modelMapper = new ModelMapper();
//        get caller entity fro dto
        CallerEntity callerEntity = modelMapper.map(callerDto, CallerEntity.class);
//        get Supervisor from Dto
        SupervisorEntity supervisorEntity = modelMapper.map(supervisor, SupervisorEntity.class);
//        set Supervisor
        callerEntity.setSupervisor(supervisorEntity);
        callerEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(callerDto.getPassword()));
        CallerEntity saved = callerDao.save(callerEntity);
        return modelMapper.map(saved, CallerDto.class);
    }


}
