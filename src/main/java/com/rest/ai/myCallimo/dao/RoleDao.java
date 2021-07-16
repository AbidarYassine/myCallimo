package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleEntity, Long> {
    public RoleEntity findByName(String name);

}
