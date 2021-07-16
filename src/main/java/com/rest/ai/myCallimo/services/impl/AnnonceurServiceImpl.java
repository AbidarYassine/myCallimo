package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.AnnonceurDao;
import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnonceurServiceImpl implements AnnonceurService {

    @Autowired
    private AnnonceurDao annonceurDao;

    @Override
    public AnnonceurEntity save(AnnonceurEntity annonceurEntity) {
        return annonceurDao.save(annonceurEntity);
    }

    @Override
    public List<AnnonceurEntity> findAll() {
        return annonceurDao.findAll();
    }

    @Override
    public AnnonceurEntity findById(Integer id) {
        return annonceurDao.findById(id).get();
    }

    @Override
    public AnnonceurEntity findByTelephone(String telephone) {
        return annonceurDao.findByTelephone(telephone);
    }
}
