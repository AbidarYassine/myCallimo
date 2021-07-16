package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.entities.CityEntity;

public interface CityService extends BaseInterface<CityEntity> {
    CityEntity findByName(String name);
}
