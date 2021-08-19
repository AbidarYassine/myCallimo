package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.CityResponse;
import com.rest.ai.myCallimo.response.UserResponse;

import java.util.List;

/***
 * Class description:
 * CityService contain the different operations on City
 * @author yassine
 * @version v.0.0.1
 */

public interface CityService extends BaseInterface<CityDto> {
    /***
     *
     * @param name name of city
     * @return city found by name
     */
    CityDto findByName(String name);

    /***
     * Delete by id
     * @param id id of city
     * @return integer response 1==succeed 0==failure
     */
    int delete(Integer id);

    /**
     * Get Supervisor By City apple get City bY ID
     * Then get sector then get supervisor
     *
     * @param id id of city
     * @return Supervisor Dto
     */
    SupervisorDto getByCity(Integer id);

    /***
     * Get List of Supervisor  for each id apple getByCity(id)
     * @param ids ids of cities
     * @return List Supervisors
     */
    List<SupervisorDto> getByCityIds(List<Integer> ids);


    /**
     * find Sector By city id
     *
     * @param id id of city
     * @return Sector Dto
     */
    SecteurDto findByCityId(Integer id);


    /***
     * Get List CallerDto by Names Cities
     * @param ids ids of cities
     * @return List Of CallerDto
     */
    List<CallerResponse> findByIds(List<Integer> ids);

    List<CityResponse> getAll();
}
