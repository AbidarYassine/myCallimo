package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CategoryDao;
import com.rest.ai.myCallimo.entities.CategoryEntity;
import com.rest.ai.myCallimo.services.facade.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return categoryDao.save(categoryEntity);
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public CategoryEntity findById(Integer id) {
        return categoryDao.findById(id).get();
    }

    @Override
    public CategoryEntity findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public List<CategoryEntity> save(List<CategoryEntity> categoryEntities) {
        return categoryDao.saveAll(categoryEntities);
    }
}
