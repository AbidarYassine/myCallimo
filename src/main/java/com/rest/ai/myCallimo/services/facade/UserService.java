package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.*;
import com.rest.ai.myCallimo.request.ChangePasswordRequest;

import java.util.List;

/***
 * Class description:
 * UserService contain the different operations on Supervisor
 * @author yassine
 * @version v.0.0.1
 */


public interface UserService {

    UserDto addUser(UserDto userDto, int role, UserDto authUser);

//    role 0==> callers, 1==> Supervisor  ,2==>Admin

    List<UserDto> findAll();


    UserDto findByEmail(String email);

    List<OffreDto> findByUserId(Integer id);

    List<SecteurDto> findSecteursBySupId(Integer id);

    List<CityDto> findCitiessBySubId(Integer id);

    List<CallerDto> findCallersByUserId(Integer id);


    UserDto changePassword(ChangePasswordRequest changePasswordRequest);


}
