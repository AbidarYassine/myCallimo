package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CityDao;
import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
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

import java.util.ArrayList;
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
        CityEntity cityEntity = cityDao.findById(id).orElseThrow(null);
        if (cityEntity == null) throw new CityNotFoundException("ville not trouver !!");
        Secteur secteur = cityEntity.getSecteur();
        if (secteur == null) throw new NotFoundException("secteur non trouver !!");
        if (secteur.getSupervisor() == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(secteur.getSupervisor(), SupervisorDto.class);
    }

    @Override
    public List<SupervisorDto> getByCityIds(List<Integer> ids) {
        List<CityEntity> cities = cityDao.findAllById(ids);
        if (cities.size() == 0) throw new NotFoundException("vile non trouver !!");
        ModelMapper modelMapper = new ModelMapper();
        return cities
                .stream()
                .filter(el -> el.getSecteur().getSupervisor() != null)
                .map(el -> modelMapper.map(el.getSecteur().getSupervisor(), SupervisorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SecteurDto findByCityId(Integer id) {
        CityDto cityDto = findById(id);
        return null; // TODO ici
    }

    @Override
    public List<CallerDto> findByIds(List<Integer> ids) {
        List<CallerDto> callerDtos = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        ids.forEach(el -> {
            CityEntity cityEntity = cityDao.findById(el).orElse(null);
            if (cityEntity != null) {
                if (cityEntity.getCaller() != null) {
                    callerDtos.add(modelMapper.map(cityEntity.getCaller(), CallerDto.class));
                }
            }
        });
        return callerDtos;
    }
}
