package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.entities.AppelEntity;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * Class description:
 * AppelService contain the different operations on Appeles
 * @author yassine
 * @version v.0.0.1
 */
public interface AppelService {


    AppelResponse save(AppelDto appelDto);

    AppelEntity findById(Integer id);

    AppelResponse updateAudio(MultipartFile file, Integer id);

    AppelResponse saveWithCaller(AppelDto appelDto, Integer id, Integer id_offre);

    AppelResponse saveWithSupervisor(AppelDto appelDto, Integer id, Integer id_offre);

    int delete(Integer id);


    List<AppelResponse> findAll();

//    String uploadAudio();


}
