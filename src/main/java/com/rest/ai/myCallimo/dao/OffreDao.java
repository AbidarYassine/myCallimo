package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.OffreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreDao extends JpaRepository<OffreEntity, Integer> {

    @Query(value = "SELECT * FROM offers WHERE is_affected_to_caller=1 || is_affected_to_supervisor=1", nativeQuery = true)
    List<OffreEntity> getOffresAfected();
}
