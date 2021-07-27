package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CityDto;

public interface CityService extends BaseInterface<CityDto> {
    CityDto findByName(String name);

    int delete(Integer id);
}
