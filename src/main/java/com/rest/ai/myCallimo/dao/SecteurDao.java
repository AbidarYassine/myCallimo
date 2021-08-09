package com.rest.ai.myCallimo.dao;

import com.rest.ai.myCallimo.entities.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurDao extends JpaRepository<Secteur, Integer> {
    Secteur findByLibelle(String libelle);

    Secteur findByCode(String code);
}
