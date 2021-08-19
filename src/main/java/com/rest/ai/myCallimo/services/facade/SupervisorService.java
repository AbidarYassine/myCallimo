package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.response.SupervisorResponse;

/***
 * Class description:
 * SupervisorService contain the different operations on Supervisor
 * @author yassine
 * @version v.0.0.1
 */


public interface SupervisorService {
    SupervisorDto save(SupervisorDto t, AdminDto admin);

    SupervisorDto save(SupervisorDto t);

    SupervisorDto findByEmail(String email);

    int delete(Integer id);

    SupervisorResponse findById(Integer id);

    int update(SupervisorDto supervisorDto, Integer id);

    public void deleteAllByEmail(String email);

    int deleteById(int id);

//    String affecterSupToSecteur(Integer sup_id, Integer secteur_id);
//
//    String affecterSupToSecteur(AffectationRequest affectationRequest);


}
