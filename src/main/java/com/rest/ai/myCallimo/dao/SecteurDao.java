package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurDao extends JpaRepository<Secteur, Integer> {
    Secteur findByLibelle(String libelle);

    Secteur findByCode(String code);

    @Query(value = "SELECT * FROM secteurs", nativeQuery = true)
    List<Secteur> getAllSecteurs();

}
