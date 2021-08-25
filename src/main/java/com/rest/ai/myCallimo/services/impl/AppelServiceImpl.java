package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.AppelDao;
import com.rest.ai.myCallimo.dao.SupervisorDao;
import com.rest.ai.myCallimo.entities.AppelEntity;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.NotFoundException;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.services.facade.AppelService;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.OffreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AppelServiceImpl implements AppelService {


    private final AppelDao appelDao;
    private final ModelMapper modelMapper;
    private final CallerService callerService;
    private final SupervisorDao supervisorDao;
    private final OffreService offreService;

    public AppelServiceImpl(AppelDao appelDao, ModelMapper modelMapper, CallerService callerService, SupervisorDao supervisorDao, OffreService offreService) {
        this.appelDao = appelDao;
        this.modelMapper = modelMapper;
        this.callerService = callerService;
        this.supervisorDao = supervisorDao;
        this.offreService = offreService;
    }

    @Override
    public AppelResponse save(AppelDto appelDto) {
        AppelEntity saved = appelDao.save(modelMapper.map(appelDto, AppelEntity.class));
        return modelMapper.map(saved, AppelResponse.class);
    }

    @Override
    public AppelResponse saveWithCaller(AppelDto appelDto, Integer id, Integer id_offre) {
        /*find caller by id*/
        CallerResponse callerResponse = callerService.findById(id);
        OffreEntity offreEntity = offreService.findByIdE(id_offre);
        /*Get Entity from dtO*/
        AppelEntity appelEntity = modelMapper.map(appelDto, AppelEntity.class);
        CallerEntity callerEntity = modelMapper.map(callerResponse, CallerEntity.class);
        /*Afectation*/
        appelEntity.setCaller(callerEntity);
        /*save in db*/
        AppelEntity saved = appelDao.save(appelEntity);
//        appelDao.updateOffreId(id_offre, saved.getId());
        return modelMapper.map(saved, AppelResponse.class);

    }

    @Override
    public AppelResponse saveWithSupervisor(AppelDto appelDto, Integer id, Integer id_offre) {
        /*find by Id*/
        SupervisorEntity supervisor = supervisorDao.findById(id).orElseThrow(() -> new NotFoundException("SUPERVISOR NOT FOUND"));
        OffreEntity offreEntity = offreService.findByIdE(id_offre);
        /*get Entity from response */
        AppelEntity appelEntity = modelMapper.map(appelDto, AppelEntity.class);
        /* Afectation */
        appelEntity.setSupervisor(supervisor);
        /**/
        AppelEntity saved = appelDao.save(appelEntity);
//        appelDao.updateOffreId(id_offre, saved.getId());
        return modelMapper.map(saved, AppelResponse.class);
    }

    @Override
    public int delete(Integer id) {
        AppelEntity appelEntity = appelDao.findById(id).orElseThrow(() -> new NotFoundException("appel non trouver "));
        appelDao.delete(appelEntity);
        return 1;
    }

    @Override
    public List<AppelResponse> findAll() {
        return appelDao.findAll()
                .stream()
                .map(el -> modelMapper.map(el, AppelResponse.class))
                .collect(Collectors.toList());
    }
}
