package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CityDao;
import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.services.facade.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public CityEntity save(CityEntity cityEntity) {
        return cityDao.save(cityEntity);
    }

    @Override
    public List<CityEntity> findAll() {
        return cityDao.findAll();
    }

    @Override
    public CityEntity findById(Integer id) {
        return cityDao.findById(id).get();
    }

    @Override
    public CityEntity findByName(String name) {
        return cityDao.findByName(name);
    }
}
