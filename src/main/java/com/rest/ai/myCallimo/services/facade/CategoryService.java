package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.entities.CategoryEntity;

import java.util.List;

public interface CategoryService {
    CategoryDto findByName(String name);

    CategoryDto save(CategoryDto categoryDto);

}
