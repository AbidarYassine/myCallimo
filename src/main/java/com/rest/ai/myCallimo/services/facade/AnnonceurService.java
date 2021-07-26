package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.AnnonceurDto;


public interface AnnonceurService extends BaseInterface<AnnonceurDto> {

    AnnonceurDto findByTelephone(String telephone);

}
