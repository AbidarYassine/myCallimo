package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.OffreTypeDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.request.AffectationRequest;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.request.search.PagedResponse;
import com.rest.ai.myCallimo.request.search.SearchRequest;
import com.rest.ai.myCallimo.response.AppelResponse;

import java.util.List;

/***
 * Class description:
 * OffreService contain the different operations on Offre
 * @author yassine
 * @version v.0.0.1
 */

public interface OffreService extends BaseInterface<OffreDto> {


    OffreEntity findByIdE(Integer id);

    OffreEntity save(OffreEntity offreEntity);

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
     * Add new offre
     * @param offreDto offreDto
     * @return Offre Saved
     */
    OffreDto save(OffreDto offreDto);

    PagedResponse<OffreDto> getOffreTriate(final SearchRequest request);

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
     * @return Page of Offre Dto No Afcted
     */
    PagedResponse<OffreDto> list(final SearchRequest request);


    /***
     * get All Offre With Pagination
     * @param request size and page
     * @return Page of Offre Dto  Afcted
     */
    PagedResponse<OffreDto> listAfected(final SearchRequest request);


    /***
     * update Category of offer
     * @param id_offre id of offer
     * @param id_category id of category
     * @return Category Dto
     */
    CategoryDto updateOffreCategory(Integer id_offre, Integer id_category);


    /***
     * update Type of offer
     * @param id_offre id of offer
     * @param id_offre_type id of Offer Type
     * @return OffreTypeDto
     */
    OffreTypeDto updateOffreType(Integer id_offre, Integer id_offre_type);


    /***
     * update information de base de l'offre
     * @param offreDto offre information
     * @return Updated Offre
     */
    OffreDto updateOffre(OffreDto offreDto);


    AppelResponse saveWithSup(Integer id, AppelDto appelDto, Integer sup_id);

    AppelResponse saveWithCaller(Integer id, AppelDto appelDto, Integer caller_id);


}
