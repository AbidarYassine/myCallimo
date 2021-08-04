package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.AffectationOffreRequest;

import java.util.List;

public interface OffreService extends BaseInterface<OffreDto> {
    SupervisorDto affecterOffreToSupervisor(AffectationOffreRequest affectationOffreRequest);

    CallerDto affecterOffreToCaller(AffectationOffreRequest affectationOffreRequest);

    UserDto getByOffre(Integer id);

    List<OffreDto> getBySupervisor(Integer id);

    List<OffreDto> getByCaller(Integer id);


}
