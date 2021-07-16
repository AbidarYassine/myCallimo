package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceurDao extends JpaRepository<AnnonceurEntity, Integer> {

    AnnonceurEntity findByTelephone(String telephone);
}
