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


//    @Query(value = "SELECT * FROM offers WHERE annonceur_id IS NOT NULL AND is_affected_to_caller=0 AND is_affected_to_supervisor=0", nativeQuery = true)
//    Page<OffreEntity> getAllOffre(PageRequest request);

    @Query("SELECT offre FROM OffreEntity offre WHERE offre.is_affected_to_caller=false AND offre.is_affected_to_supervisor=false  AND  offre.annonceur.telephone != null AND offre.annonceur.telephone!= '' ")
    Page<OffreEntity> getAllOffre(PageRequest pageableRequest);
}
