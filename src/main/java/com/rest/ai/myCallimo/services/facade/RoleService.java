package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.RoleDto;


public interface RoleService {

    public RoleDto findByName(String name) ;

    public RoleDto save(RoleDto roleDto);
}
