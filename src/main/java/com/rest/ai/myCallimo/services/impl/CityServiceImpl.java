package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CityDao;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.entities.Secteur;
import com.rest.ai.myCallimo.exception.NotFoundException;
import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.CityService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityServiceImpl implements CityService {

    private CityDao cityDao;

    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public CityDto save(CityDto cityDto) {
        ModelMapper modelMapper = new ModelMapper();
        CityEntity savedCity = cityDao.save(modelMapper.map(cityDto, CityEntity.class));
        return modelMapper.map(savedCity, CityDto.class);
    }

    @Override
    public List<CityDto> findAll() {
        List<CityEntity> cities = cityDao.findAll();
        log.info("Size is {}", cities.size());
        ModelMapper modelMapper = new ModelMapper();
        return cities
                .stream()
                .map(el -> modelMapper.map(el, CityDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CityDto findById(Integer id) {
        CityEntity cityEntity = cityDao.findById(id).orElse(null);
        if (cityEntity == null) throw new UserNotFoundException("ville non trouver par id " + id + " !!");
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cityEntity, CityDto.class);
    }

    @Override
    public CityDto findByName(String name) {
        CityEntity cityEntity = cityDao.findByName(name);
        if (cityEntity == null) throw new UserNotFoundException("ville non trouver par " + name + " !!");
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cityEntity, CityDto.class);
    }

    @Override
    @Transactional
    public int delete(Integer id) {
        CityEntity cityEntity = cityDao.findById(id).orElse(null);
        if (cityEntity == null) return -1;
        cityDao.delete(cityEntity);
        return 1;
    }

    @Override
    public SupervisorDto getByCity(Integer id) {
        CityEntity cityEntity = cityDao.findById(id).orElse(null);
        if (cityEntity == null) throw new CityNotFoundException("ville not trouver !!");
        Secteur secteur = cityEntity.getSecteur();
        if (secteur == null) throw new NotFoundException("secteur non trouver !!");
        if (secteur.getSupervisor() == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(secteur.getSupervisor(), SupervisorDto.class);
    }
}
