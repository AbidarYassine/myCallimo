package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.CallerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CallerDao extends JpaRepository<CallerEntity, Integer> {
    public CallerEntity findByEmail(String email);

    @Modifying
    @Query("DELETE FROM CallerEntity c WHERE c.id = ?1")
    void deleteById(int id);

}
