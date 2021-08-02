package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.request.AffectationOffreRequest;

public interface OffreService extends BaseInterface<OffreDto> {
    SupervisorDto affecterOffreToSupervisor(AffectationOffreRequest affectationOffreRequest);

}
