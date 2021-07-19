package com.rest.ai.myCallimo.dao;


import com.rest.ai.myCallimo.entities.SupervisorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorDao extends JpaRepository<SupervisorEntity, Integer> {
    public SupervisorEntity findByEmail(String email);

}
