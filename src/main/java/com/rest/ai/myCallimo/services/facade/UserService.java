package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.ChangePasswordRequest;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.CityResponse;
import com.rest.ai.myCallimo.response.SecteurResponse;

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

    List<SecteurResponse> findSecteursBySupId(Integer id);

    List<CityResponse> findCitiessBySubId(Integer id);

    List<CallerResponse> findCallersByUserId(Integer id);


    UserDto changePassword(ChangePasswordRequest changePasswordRequest);


}
