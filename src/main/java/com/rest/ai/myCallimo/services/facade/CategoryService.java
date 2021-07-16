package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.entities.CategoryEntity;

import java.util.List;

public interface CategoryService extends BaseInterface<CategoryEntity> {
    CategoryEntity findByName(String name);

    List<CategoryEntity> save(List<CategoryEntity> categoryEntities);

}
