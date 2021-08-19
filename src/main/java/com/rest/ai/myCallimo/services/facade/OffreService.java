package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.AffectationRequest;
import com.rest.ai.myCallimo.request.search.PagedResponse;
import com.rest.ai.myCallimo.request.search.SearchRequest;

import java.util.List;
/***
 * Class description:
 * OffreService contain the different operations on Offre
 * @author yassine
 * @version v.0.0.1
 */

public interface OffreService extends BaseInterface<OffreDto> {


    /***
     * Afectation des offres  a un superviseur
     * @param affectationOffreRequest  The  necessary Dat for Afectation
     * @return To supervisor who we affected offers
     */
    String affecterOffreToSupervisor(AffectationRequest affectationOffreRequest);

    /***
     * Afectation des offres a un Caller
     * @param affectationOffreRequest The  necessary Dat for Afectation
     * @return To supervisor who we affected offers
     */
    String affecterOffreToCaller(AffectationRequest affectationOffreRequest);

    /***
     * Get User Afected to Offre id
     * @param id Offre id
     * @return UserDto
     */
    UserDto getByOffre(Integer id);

    /***
     * Get All Offre Afected to supervisor id
     * @param id id of supervisor
     * @return List Of Offre Dto Afecte to Supervisor
     */
    List<OffreDto> getBySupervisor(Integer id);

    /***
     * Get All Offre Afected to Caller id
     * @param id id of Caller
     * @return List Of Offre Dto Afecte to Caller
     */
    List<OffreDto> getByCaller(Integer id);

    /***
     * Add new offre
     * @param offreDto offreDto
     * @return Offre Saved
     */
    OffreDto save(OffreDto offreDto);

    /***
     * Delete Caller By Id
     * @param id id of Caller
     * @return integer response 1==deleted 0== failure
     */
    int deleteCaller(Integer id);

    /***
     * get Offres By Ids
     * @param ids ids of offres
     * @return List Offres Dto
     */
    List<OffreDto> findByIds(List<Integer> ids);


    /***
     * get All Offre With Pagination
     * @param request size and page
     * @return Page of Offre Dto
     */
    PagedResponse<OffreDto> list(final SearchRequest request);

    PagedResponse<OffreDto> listNoAfected(final SearchRequest request);


}
