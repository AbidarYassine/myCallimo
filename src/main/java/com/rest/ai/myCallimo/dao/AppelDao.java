package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.AppelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppelDao extends JpaRepository<AppelEntity, Integer> {
}
