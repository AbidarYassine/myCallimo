package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;

public interface CityService extends BaseInterface<CityDto> {
    CityDto findByName(String name);

    int delete(Integer id);

    SupervisorDto getByCity(Integer id);
}
