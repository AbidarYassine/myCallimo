package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<CityEntity, Integer> {

    CityEntity findByName(String name);
}
