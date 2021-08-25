package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.CategoryDto;

import java.util.List;

/***
 * Class description:
 * CategoryService contain the different operations on category
 * @author yassine
 * @version v.0.0.1
 */

public interface CategoryService {
    /***
     *
     * @param name name of category
     * @return category dto
     */
    CategoryDto findByName(String name);

    /***
     * Add new Category
     * @param categoryDto Category Dto
     * @return Saved Category Dto
     */
    CategoryDto save(CategoryDto categoryDto);

    List<CategoryDto> findAll();

    CategoryDto findById(Integer id);

}
