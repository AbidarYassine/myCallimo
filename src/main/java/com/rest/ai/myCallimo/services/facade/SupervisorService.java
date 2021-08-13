package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.request.AffectationRequest;

import java.util.List;


public interface SupervisorService {
    SupervisorDto save(SupervisorDto t, AdminDto admin);

    SupervisorDto save(SupervisorDto t);

    SupervisorDto findByEmail(String email);

    int delete(Integer id);

    SupervisorDto findById(Integer id);

    int update(SupervisorDto supervisorDto, Integer id);

    public void deleteAllByEmail(String email);

    int deleteById(int id);

    SecteurDto affecterSupToSecteur(Integer sup_id, Integer secteur_id);

    List<SecteurDto> affecterSupToSecteur(AffectationRequest affectationRequest);


}
