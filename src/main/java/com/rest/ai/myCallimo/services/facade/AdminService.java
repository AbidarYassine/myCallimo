package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.response.AdminResponse;


/***
 * Class description:
 - Service interface that represents Admin
 * Admin Service
 */
public interface AdminService extends BaseInterface<AdminDto> {

    /***
     *  get Admin By Email
     * @param email of  admin
     * @return AdminResponse
     */
    AdminResponse findByEmail(String email);

}
