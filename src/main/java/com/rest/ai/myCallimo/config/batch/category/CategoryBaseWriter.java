package com.rest.ai.myCallimo.config.batch.category;


import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.entities.CategoryEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import com.rest.ai.myCallimo.services.facade.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CategoryBaseWriter implements ItemWriter<CategoryEntity> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public void write(List<? extends CategoryEntity> list) throws Exception {
        list.forEach(categoryEntity -> {
            log.info("Enregistrement en base de l'objet {}", categoryEntity);
            ModelMapper modelMapper = new ModelMapper();
            categoryService.save(modelMapper.map(categoryEntity, CategoryDto.class));
        });
    }
}
