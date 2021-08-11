package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;

import java.util.List;

public interface SecteurService extends BaseInterface<SecteurDto> {
    SecteurDto findByLibelle(String libelle);

    SecteurDto findByCode(String code);


    List<CityDto> getBySecteurId(Integer id);

    List<SecteurDto> getSecteurNonAfecter();

    List<SecteurDto> getSecteurAfected();

    public void updateSecteur();


}
