package com.rest.ai.myCallimo.controllers;

import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.services.facade.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/name/{name}")
    public CategoryDto findByName(@PathVariable() String name) {
        return categoryService.findByName(name);
    }

    @PostMapping("")
    public CategoryDto save(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }
}
