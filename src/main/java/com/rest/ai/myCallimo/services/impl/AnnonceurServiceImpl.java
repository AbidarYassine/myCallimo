package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.AnnonceurDao;
import com.rest.ai.myCallimo.dto.AnnonceurDto;
import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import com.rest.ai.myCallimo.exception.NotFoundException;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnonceurServiceImpl implements AnnonceurService {


    private AnnonceurDao annonceurDao;
    private ModelMapper modelMapper;

    public AnnonceurServiceImpl(AnnonceurDao annonceurDao, ModelMapper modelMapper) {
        this.annonceurDao = annonceurDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnnonceurDto findByTelephone(String telephone) {
        AnnonceurEntity annonceurEntity = annonceurDao.findByTelephone(telephone);
        if (annonceurEntity != null)
            return modelMapper.map(annonceurEntity, AnnonceurDto.class);
        return null;
    }

    @Override
    public AnnonceurDto update(AnnonceurDto annonceurDto) {
        AnnonceurEntity annonceurEntity = annonceurDao.findById(annonceurDto.getId())
                .orElseThrow(() -> new NotFoundException("Annonceur not found"));
        if (annonceurDto.getAddress() != null && annonceurDto.getAddress().length() > 0) {
            annonceurEntity.setAddress(annonceurDto.getAddress());
        }
        if (annonceurDto.getEmail() != null && annonceurDto.getEmail().length() > 0) {
            annonceurEntity.setEmail(annonceurDto.getEmail());
        }
        if (annonceurDto.getName() != null && annonceurDto.getName().length() > 0) {
            annonceurEntity.setName(annonceurDto.getName());
        }
        return modelMapper.map(annonceurDao.save(annonceurEntity), AnnonceurDto.class);
    }

    @Override
    public AnnonceurDto save(AnnonceurDto annonceurDto) {

        AnnonceurEntity annonceurEntity = modelMapper.map(annonceurDto, AnnonceurEntity.class);
        AnnonceurEntity saved = annonceurDao.save(annonceurEntity);
        return modelMapper.map(saved, AnnonceurDto.class);
    }

    @Override
    public List<AnnonceurDto> findAll() {
        List<AnnonceurEntity> entities = annonceurDao.findAll();

        return entities
                .stream()
                .map(el -> modelMapper.map(el, AnnonceurDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnnonceurDto findById(Integer id) {
        AnnonceurEntity annonceurEntity = annonceurDao.findById(id).orElse(null);
        if (annonceurEntity == null) return null;

        return modelMapper.map(annonceurEntity, AnnonceurDto.class);
    }
}
