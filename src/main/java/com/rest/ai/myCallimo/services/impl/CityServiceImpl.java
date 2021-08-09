package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CityDao;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.services.facade.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        ModelMapper modelMapper = new ModelMapper();
        return cities
                .stream()
                .map(el -> modelMapper.map(el, CityDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CityDto findById(Integer id) {
        CityEntity cityEntity = cityDao.findById(id).orElse(null);
        if (cityEntity == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cityEntity, CityDto.class);
    }

    @Override
    public CityDto findByName(String name) {
        CityEntity cityEntity = cityDao.findByName(name);
        if (cityEntity == null) return null;
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
}
