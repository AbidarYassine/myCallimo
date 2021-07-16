package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);

}
