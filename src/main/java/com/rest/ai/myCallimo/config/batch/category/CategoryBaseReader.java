package com.rest.ai.myCallimo.config.batch.category;


import com.rest.ai.myCallimo.entities.base.CategoryBase;
import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import com.rest.ai.myCallimo.shared.Utils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryBaseReader implements ItemReader<CategoryBase> {
    private final String apiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    Utils utils;

    public CategoryBaseReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    private int nextCategoryIndex;
    private List<CategoryBase> categoryEntities;

    //    return
    @Override
    public CategoryBase read() throws Exception {
        if (studentDataIsNotInitialized()) {
            categoryEntities = fetchStudentDataFromAPI();
        }
        CategoryBase nextCategory = null;

        if (nextCategoryIndex < categoryEntities.size()) {
            nextCategory = categoryEntities.get(nextCategoryIndex);
            nextCategoryIndex++;
        } else {
            nextCategoryIndex = 0;
            categoryEntities = null;
        }

        return nextCategory;
    }

    private boolean studentDataIsNotInitialized() {
        return this.categoryEntities == null;
    }

    private List<CategoryBase> fetchStudentDataFromAPI() {
        ResponseEntity<CategoryBase[]> response = restTemplate.getForEntity(apiUrl,
                CategoryBase[].class
        );
        CategoryBase[] categoryEntities = response.getBody();
        return Arrays.stream(categoryEntities).filter(x -> x.getOffer_category() != null).filter(utils.distinctByKey(CategoryBase::getOffer_category)).collect(Collectors.toList());
    }
}
