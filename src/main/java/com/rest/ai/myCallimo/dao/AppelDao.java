package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.AppelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppelDao extends JpaRepository<AppelEntity, Integer> {

    @Query("UPDATE appeles SET offre_id =:offre_id  WHERE id=:id")
    @Modifying
    void updateOffreId(@Param("offre_id") Integer offre_id, @Param("id") Integer id);
}
