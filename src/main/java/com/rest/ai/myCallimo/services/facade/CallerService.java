package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.UserResponse;

import java.util.List;

/***
 * Class description:
 * CallerService contain the different operations on callers
 * @author yassine
 * @version v.0.0.1
 */

public interface CallerService {
    /***
     * find By Email
     * @param email email to search caller
     * @return CallerDto
     */
    CallerDto findByEmail(String email);

    /***
     * find by id
     * @param id id to search caller
     * @return Found Caller
     */
    CallerResponse findById(Integer id);

    /***
     * Add new caller
     * @param callerDto les information du caller
     * @param supervisor_id belong to supervisor
     * @return CallerDto
     */
    CallerResponse save(CallerDto callerDto, Integer supervisor_id);

    /***
     * Add new Caller
     * @param callerDto les information du caller
     * @return Saved Caller
     */
    CallerDto save(CallerDto callerDto);

//    List<CallerDto> getBySupervisorId(Integer id);

    /***
     * Get All Callers
     * @return tout les callers
     */
    List<UserResponse> getAll();

    /***
     * delete caller by id
     * @param id id to delete caller
     * @return integer response
     */
    int deleteById(int id);


}
