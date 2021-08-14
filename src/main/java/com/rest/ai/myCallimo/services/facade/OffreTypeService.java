package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.OffreTypeDto;


/***
 * Class description:
 * OffreTypeService contain the different operations on Offre Type
 * @author yassine
 * @version v.0.0.1
 */


public interface OffreTypeService extends BaseInterface<OffreTypeDto> {

    /***
     * Find Offre Type by type
     * @param type type
     * @return OffreTypeDto
     */
    OffreTypeDto findByType(String type);
}
