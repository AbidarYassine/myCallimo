package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.AffectationRequest;
import com.rest.ai.myCallimo.request.search.PagedResponse;
import com.rest.ai.myCallimo.request.search.SearchRequest;

import java.util.List;

public interface OffreService extends BaseInterface<OffreDto> {
    SupervisorDto affecterOffreToSupervisor(AffectationRequest affectationOffreRequest);

    CallerDto affecterOffreToCaller(AffectationRequest affectationOffreRequest);

    UserDto getByOffre(Integer id);

    List<OffreDto> getBySupervisor(Integer id);

    List<OffreDto> getByCaller(Integer id);

    OffreDto save(OffreDto offreDto);

    int deleteCaller(Integer id);

    List<OffreDto> findByIds(List<Integer> ids);


    PagedResponse<OffreDto> list(final SearchRequest request);


}
