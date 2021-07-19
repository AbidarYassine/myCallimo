package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.CallerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallerDao extends JpaRepository<CallerEntity, Integer> {
    public CallerEntity findByEmail(String email);

}
