package com.rest.ai.myCallimo.services.impl;


import com.rest.ai.myCallimo.dao.OffreTypeDao;
import com.rest.ai.myCallimo.dto.OffreTypeDto;
import com.rest.ai.myCallimo.entities.OffreTypeEntity;
import com.rest.ai.myCallimo.services.facade.OffreTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreTypeServiceImpl implements OffreTypeService {

    @Autowired
    private OffreTypeDao offreTypeDao;


    @Override
    public OffreTypeDto save(OffreTypeDto offreTypeDto) {
        if (this.findByType(offreTypeDto.getType()) != null) return null;
        ModelMapper modelMapper = new ModelMapper();
        OffreTypeEntity offreTypeEntity = modelMapper.map(offreTypeDto, OffreTypeEntity.class);
        OffreTypeEntity saved = offreTypeDao.save(offreTypeEntity);
        return modelMapper.map(saved, OffreTypeDto.class);
    }

    @Override
    public List<OffreTypeDto> findAll() {
        List<OffreTypeEntity> offreTypeEntities = offreTypeDao.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return offreTypeEntities
                .stream()
                .map(el -> modelMapper.map(el, OffreTypeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OffreTypeDto findById(Integer id) {
        OffreTypeEntity offreTypeEntity = offreTypeDao.findById(id).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(offreTypeEntity, OffreTypeDto.class);
    }

    @Override
    public OffreTypeDto findByType(String type) {
        OffreTypeEntity offreTypeEntity = offreTypeDao.findByType(type);
        ModelMapper modelMapper = new ModelMapper();
        if (offreTypeEntity != null)
            return modelMapper.map(offreTypeEntity, OffreTypeDto.class);
        return null;

    }
}
