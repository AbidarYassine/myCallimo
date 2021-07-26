package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.UserDto;

public interface AuthRoleService {

    boolean isAuthorized(String role);

    UserDto getUserAuth();

    String getRole();
}
