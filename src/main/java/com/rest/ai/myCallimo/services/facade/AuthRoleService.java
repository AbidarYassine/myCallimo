package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.UserDto;

/***
 * * Class description:
 - Service interface that represents Role
 * Role Service
 */
public interface AuthRoleService {


    /**
     * @return user logged
     */
    UserDto getUserAuth();

    /**
     * @return Role of user connected
     */
    String getRole();
}
