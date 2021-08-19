package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.response.CallerResponse;

import java.util.List;

/***
 *  Class description:
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

    /***
     * get Callers by user connected
     * @return List of caller
     */
    List<CallerResponse> getCallers();


}
