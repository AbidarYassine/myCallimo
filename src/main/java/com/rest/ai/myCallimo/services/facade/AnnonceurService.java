package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.entities.AnnonceurEntity;


public interface AnnonceurService extends BaseInterface<AnnonceurEntity> {

    AnnonceurEntity findByTelephone(String telephone);

}
