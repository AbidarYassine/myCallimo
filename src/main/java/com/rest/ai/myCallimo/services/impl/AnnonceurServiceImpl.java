package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.AnnonceurDao;
import com.rest.ai.myCallimo.dto.AnnonceurDto;
import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnonceurServiceImpl implements AnnonceurService {


    private AnnonceurDao annonceurDao;

    public AnnonceurServiceImpl(AnnonceurDao annonceurDao) {
        this.annonceurDao = annonceurDao;
    }

    @Override
    public AnnonceurDto findByTelephone(String telephone) {
        AnnonceurEntity annonceurEntity = annonceurDao.findByTelephone(telephone);
        ModelMapper modelMapper = new ModelMapper();
        if (annonceurEntity != null)
            return modelMapper.map(annonceurEntity, AnnonceurDto.class);
        return null;
    }

    @Override
    public AnnonceurDto save(AnnonceurDto annonceurDto) {
        ModelMapper modelMapper = new ModelMapper();
        AnnonceurEntity annonceurEntity = modelMapper.map(annonceurDto, AnnonceurEntity.class);
        AnnonceurEntity saved = annonceurDao.save(annonceurEntity);
        return modelMapper.map(saved, AnnonceurDto.class);
    }

    @Override
    public List<AnnonceurDto> findAll() {
        List<AnnonceurEntity> entities = annonceurDao.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return entities
                .stream()
                .map(el -> modelMapper.map(el, AnnonceurDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnnonceurDto findById(Integer id) {
        AnnonceurEntity annonceurEntity = annonceurDao.findById(id).orElse(null);
        if (annonceurEntity == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(annonceurEntity, AnnonceurDto.class);
    }
}
