package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findByName(String name);
}
