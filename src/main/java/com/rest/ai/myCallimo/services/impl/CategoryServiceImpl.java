package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CategoryDao;
import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.entities.CategoryEntity;
import com.rest.ai.myCallimo.exception.NotFoundException;
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
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto findByName(String name) {
        CategoryEntity categoryEntity = categoryDao.findByName(name);

        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
        CategoryEntity savedCategory = categoryDao.save(categoryEntity);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(el -> modelMapper.map(el, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Integer id) {
        CategoryEntity categoryEntity = categoryDao.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }
}
