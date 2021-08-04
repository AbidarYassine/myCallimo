package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.CityService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CallerServiceImpl implements CallerService {


    private final CallerDao callerDao;

    private final UserService userService;
    private final SupervisorService supervisorService;
    private final CityService cityService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CallerServiceImpl(CallerDao callerDao, UserService userService, SupervisorService supervisorService, CityService cityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.callerDao = callerDao;
        this.userService = userService;
        this.supervisorService = supervisorService;
        this.cityService = cityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CallerDto findByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        CallerEntity callerEntity = callerDao.findByEmail(email);
        return modelMapper.map(callerEntity, CallerDto.class);
    }

    @Override
    public CallerDto findById(Integer id) {
        CallerEntity callerEntity = callerDao.findById(id).orElse(null);
        if (callerEntity == null) throw new UserNotFoundException("agent non trouver par l'id " + id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(callerEntity, CallerDto.class);
    }

    @Override
    public CallerDto save(CallerDto callerDto, Integer supervisor_id) {
        SupervisorDto supervisorDto = supervisorService.findById(supervisor_id);
        if (supervisorDto == null) {
            throw new UserNotFoundException("supervisor  non trouver par  id " + supervisor_id);
        }
        CityDto cityDto = cityService.findById(callerDto.getCity_id());
        if (cityDto == null) throw new CityNotFoundException("City no trouver !!");
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(supervisorDto, SupervisorEntity.class);
        if (userService.findByEmail(callerDto.getEmail()) != null) {
            throw new UserAlreadyExist("Utilisaeur avec l'email  " + callerDto.getEmail() + " déjà existe ");
        }

//        get caller entity fro dto
        CallerEntity callerEntity = modelMapper.map(callerDto, CallerEntity.class);
//        set Supervisor
        callerEntity.setSupervisor(supervisorEntity);
        //        get city entity from city dto
        CityEntity cityEntity = modelMapper.map(cityDto, CityEntity.class);
//        set city
        callerEntity.setCity(cityEntity);
        callerEntity.setAvatar("https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_960_720.png");
        callerEntity.setPassword(bCryptPasswordEncoder.encode(callerDto.getPassword()));
        CallerEntity saved = callerDao.save(callerEntity);
        return modelMapper.map(saved, CallerDto.class);
    }

    @Override
    public CallerDto save(CallerDto callerDto) {
        ModelMapper modelMapper = new ModelMapper();
        CallerEntity callerEntity = modelMapper.map(callerDto, CallerEntity.class);
        CallerEntity saved = callerDao.save(callerEntity);
        return modelMapper.map(saved, CallerDto.class);
    }

    @Override
    public List<CallerDto> getBySupervisorId(Integer id) {
        SupervisorDto supervisorDto = supervisorService.findById(id);
        if (supervisorDto == null) return null;
        return supervisorDto.getCallers();
    }

    @Override
    public List<CallerDto> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        return callerDao.findAll().stream().map(el -> modelMapper.map(el, CallerDto.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        CallerEntity callerEntity = callerDao.findById(id).orElse(null);
        if (callerEntity == null) throw new UserNotFoundException("Agent not found par l'id " + id);
        callerDao.deleteById(id);
        return 1;
    }

//
//    List<Customer> customersWithMoreThan100Points = customers
//            .stream()
//            .filter(c -> c.getPoints() > 100)
//            .collect(Collectors.toList());


    @Transactional
    @Override
    public int retireCaller(Integer id) {
        CallerEntity callerEntity = callerDao.findById(id).orElse(null);
        if (callerEntity == null) throw new UserNotFoundException("Agent not found par l'id " + id);
        SupervisorEntity supervisorEntity = callerEntity.getSupervisor();
        if (supervisorEntity != null) {
            supervisorEntity.setCallers(supervisorEntity.getCallers().stream().filter(el -> !el.getId().equals(id)).collect(Collectors.toList()));
            callerEntity.setSupervisor(supervisorEntity);
            callerDao.save(callerEntity);
        } else {
            callerDao.delete(callerEntity);
        }
        return 1;
    }


}
