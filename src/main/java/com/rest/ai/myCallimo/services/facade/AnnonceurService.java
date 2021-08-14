package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.AnnonceurDto;

/***
 * Class description:
 - Service interface that represents Annonceur
 * Annonceur Service
 */
public interface AnnonceurService extends BaseInterface<AnnonceurDto> {

    /***
     *
     * @param telephone  telephone of annonceur
     * @return
     */
    AnnonceurDto findByTelephone(String telephone);

}
