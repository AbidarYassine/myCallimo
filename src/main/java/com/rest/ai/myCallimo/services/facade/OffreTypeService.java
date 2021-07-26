package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.OffreTypeDto;



public interface OffreTypeService extends BaseInterface<OffreTypeDto> {
    OffreTypeDto findByType(String type);
}
