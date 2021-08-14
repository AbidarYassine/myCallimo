package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.AdminDto;


/***
 * Class description:
 - Service interface that represents Admin
 * Admin Service
 */
public interface AdminService extends BaseInterface<AdminDto> {

    /***
     *  get Admin By Email
     * @param email of  admin
     * @return AdminDto
     */
    AdminDto findByEmail(String email);

}
