package com.rest.ai.myCallimo.config.batch.category;


import com.rest.ai.myCallimo.entities.CategoryEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.entities.base.CategoryBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import org.springframework.batch.item.ItemProcessor;

public class CategoryBaseProcessor implements ItemProcessor<CategoryBase, CategoryEntity> {

    @Override
    public CategoryEntity process(CategoryBase categoryBase) throws Exception {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryBase.getOffer_category());
        return categoryEntity;
    }
}
