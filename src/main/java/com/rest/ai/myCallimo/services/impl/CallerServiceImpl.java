package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.NotFoundException;
import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.UserResponse;
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
    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CallerServiceImpl(CallerDao callerDao, UserService userService, SupervisorService supervisorService, CityService cityService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.callerDao = callerDao;
        this.userService = userService;
        this.supervisorService = supervisorService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CallerDto findByEmail(String email) {
        CallerEntity callerEntity = callerDao.findByEmail(email);
        return modelMapper.map(callerEntity, CallerDto.class);
    }

    @Override
    public CallerResponse findById(Integer id) {
        CallerEntity callerEntity = callerDao.findById(id).orElse(null);
        if (callerEntity == null) throw new UserNotFoundException("agent non trouver par l'id " + id);

        return modelMapper.map(callerEntity, CallerResponse.class);
    }

    @Override
    public CallerResponse save(CallerDto callerDto, Integer supervisor_id) {
        SupervisorResponse supervisorDto = supervisorService.findById(supervisor_id);
        if (supervisorDto == null) {
            throw new UserNotFoundException("supervisor  non trouver par  id " + supervisor_id);
        }
        CityDto cityDto = cityService.findById(callerDto.getCity_id());
        if (cityDto == null) throw new CityNotFoundException("City no trouver !!");

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
        return modelMapper.map(saved, CallerResponse.class);
    }

    @Override
    public CallerDto save(CallerDto callerDto) {

        CallerEntity callerEntity = modelMapper.map(callerDto, CallerEntity.class);
        CallerEntity saved = callerDao.save(callerEntity);
        return modelMapper.map(saved, CallerDto.class);
    }

//    @Override
//    public List<CallerDto> getBySupervisorId(Integer id) {
//        SupervisorDto supervisorDto = supervisorService.findById(id);
//        if (supervisorDto == null) return null;
//        return supervisorDto.getCallers();
//    }

    @Override
    public List<UserResponse> getAll() {
        return callerDao.findAll().stream().map(el -> modelMapper.map(el, UserResponse.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        CallerEntity callerEntity = callerDao.findById(id).orElse(null);
        if (callerEntity == null) throw new UserNotFoundException("Agent not found par l'id " + id);
        callerDao.deleteById(id);
        return 1;
    }

    @Override
    public List<AppelResponse> getAppeles(Integer id) {
        CallerEntity callerEntity = callerDao.findById(id).orElseThrow(() -> new NotFoundException("Caller Not Found"));
        return callerEntity.getAppeles()
                .stream()
                .map(el -> modelMapper.map(el, AppelResponse.class))
                .collect(Collectors.toList());
    }


//    @Transactional
//    @Override
//    public int retireCaller(Integer id) {
//        CallerEntity callerEntity = callerDao.findById(id).orElse(null);
//        if (callerEntity == null) throw new UserNotFoundException("Agent not found par l'id " + id);
//        SupervisorEntity supervisorEntity = callerEntity.getSupervisor();
//        if (supervisorEntity != null) {
//            supervisorEntity.setCallers(supervisorEntity.getCallers().stream().filter(el -> !el.getId().equals(id)).collect(Collectors.toList()));
//            callerEntity.setSupervisor(supervisorEntity);
//            callerDao.save(callerEntity);
//        } else {
//            callerDao.delete(callerEntity);
//        }
//        return 1;
//    }


}
