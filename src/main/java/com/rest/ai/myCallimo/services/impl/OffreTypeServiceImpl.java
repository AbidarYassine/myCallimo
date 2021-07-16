package com.rest.ai.myCallimo.services.impl;


import com.rest.ai.myCallimo.dao.OffreTypeDao;
import com.rest.ai.myCallimo.entities.OffreTypeEntity;
import com.rest.ai.myCallimo.services.facade.OffreTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreTypeServiceImpl implements OffreTypeService {
    @Autowired
    private OffreTypeDao offreTypeDao;

    @Override
    public OffreTypeEntity save(OffreTypeEntity offreTypeEntity) {
        return offreTypeDao.save(offreTypeEntity);
    }

    @Override
    public List<OffreTypeEntity> findAll() {
        return offreTypeDao.findAll();
    }

    @Override
    public OffreTypeEntity findById(Integer id) {
        return offreTypeDao.getById(id);
    }
}
