package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.entities.Secteur;
import com.rest.ai.myCallimo.response.SupervisorSecteurResponse;

import java.util.List;
/***
 * Class description:
 * SecteurService contain the different operations on Secteurs
 * @author yassine
 * @version v.0.0.1
 */


public interface SecteurService extends BaseInterface<SecteurDto> {
    SecteurDto findByLibelle(String libelle);

    SecteurDto findByCode(String code);


    List<CityDto> getBySecteurId(Integer id);

    List<SecteurDto> getSecteurNonAfecter();

    List<SecteurDto> getSecteurAfected();

    public void updateSecteur();


    public List<SupervisorSecteurResponse> getBySecteurCodes(List<String> ids);


    /***
     * get All secteurs by native query
     * @return All Secteurs
     */
    List<SecteurDto> getAllSecteurs();


}
