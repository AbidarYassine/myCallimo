package com.rest.ai.myCallimo.dao;


import com.rest.ai.myCallimo.entities.OffreTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreTypeDao extends JpaRepository<OffreTypeEntity, Integer> {
    OffreTypeEntity findByType(String type);
}
