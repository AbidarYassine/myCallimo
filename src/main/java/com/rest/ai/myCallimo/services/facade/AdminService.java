package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.AdminDto;


public interface AdminService extends BaseInterface<AdminDto> {
    AdminDto findByEmail(String email);

}
