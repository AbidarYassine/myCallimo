package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<AdminEntity, Integer> {
    public AdminEntity findByEmail(String email);

}
