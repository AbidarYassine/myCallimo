package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CategoryDao;
import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.entities.CategoryEntity;
import com.rest.ai.myCallimo.services.facade.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;


    @Override
    public CategoryDto findByName(String name) {
        CategoryEntity categoryEntity = categoryDao.findByName(name);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        ModelMapper modelMapper = new ModelMapper();
        CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
        CategoryEntity savedCategory = categoryDao.save(categoryEntity);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }
}
