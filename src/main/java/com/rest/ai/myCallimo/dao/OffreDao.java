package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.request.search.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreDao extends JpaRepository<OffreEntity, Integer> {

    @Query(value = "SELECT * FROM offers WHERE is_affected_to_caller=1 || is_affected_to_supervisor=1", nativeQuery = true)
    Page<OffreEntity> getOffresAfected(PageRequest request);
}
