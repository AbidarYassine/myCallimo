package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.OffreDao;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.services.facade.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreServiceImpl implements OffreService {
    @Autowired
    private OffreDao offreDao;

    @Override
    public OffreEntity save(OffreEntity offreEntity) {
        return offreDao.save(offreEntity);
    }

    @Override
    public List<OffreEntity> findAll() {
        return offreDao.findAll();
    }

    @Override
    public OffreEntity findById(Integer id) {
        return offreDao.findById(id).get();
    }
}
