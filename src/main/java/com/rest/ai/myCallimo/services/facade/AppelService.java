package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;

import java.util.List;

/***
 * Class description:
 * AppelService contain the different operations on Appeles
 * @author yassine
 * @version v.0.0.1
 */
public interface AppelService {


    AppelResponse save(AppelDto appelDto);

    AppelResponse saveWithCaller(AppelDto appelDto, Integer id, Integer id_offre);

    AppelResponse saveWithSupervisor(AppelDto appelDto, Integer id, Integer id_offre);

    int delete(Integer id);


    List<AppelResponse> findAll();

//    String uploadAudio();


}
