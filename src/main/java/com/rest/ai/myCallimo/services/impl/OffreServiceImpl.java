package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.OffreDao;
import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.offre.AlreadyAffectedException;
import com.rest.ai.myCallimo.exception.offre.OffreNotFoundException;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.request.AffectationOffreRequest;
import com.rest.ai.myCallimo.services.facade.CallerService;
import com.rest.ai.myCallimo.services.facade.OffreService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreServiceImpl implements OffreService {

    private OffreDao offreDao;
    private SupervisorService supervisorService;
    private CallerService callerService;

    public OffreServiceImpl(OffreDao offreDao, SupervisorService supervisorService, CallerService callerService) {
        this.offreDao = offreDao;
        this.supervisorService = supervisorService;
        this.callerService = callerService;
    }

    @Override
    public OffreDto save(OffreDto offreDto) {
        ModelMapper modelMapper = new ModelMapper();
        OffreEntity offreEntity = offreDao.save(modelMapper.map(offreDto, OffreEntity.class));
        OffreEntity saved = offreDao.save(offreEntity);
        return modelMapper.map(saved, OffreDto.class);
    }

    @Override
    public List<OffreDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        return offreDao.findAll().stream().map(el -> modelMapper.map(el, OffreDto.class)).collect(Collectors.toList());
    }

    @Override
    public OffreDto findById(Integer id) {
        OffreEntity offreEntity = offreDao.findById(id).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(offreEntity, OffreDto.class);
    }


    //    affectation des offres aux supervisor
    @Override
    public SupervisorDto affecterOffreToSupervisor(AffectationOffreRequest affectationOffreRequest) {
        List<OffreEntity> offres = validateOffreRequest(affectationOffreRequest.getOffres_ids(), true);
        SupervisorDto supervisorDto = supervisorService.findById(affectationOffreRequest.getId());
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(supervisorDto, SupervisorEntity.class);
        //        set offre is afected
        offres.forEach(el -> {
            el.set_affected_to_supervisor(true);
            el.setSupervisor(supervisorEntity);
            offreDao.save(el);
        });
        return modelMapper.map(supervisorEntity, SupervisorDto.class);
    }

    @Override
    public CallerDto affecterOffreToCaller(AffectationOffreRequest affectationOffreRequest) {
        List<OffreEntity> offres = validateOffreRequest(affectationOffreRequest.getOffres_ids(), false);
        CallerDto callerDto = callerService.findById(affectationOffreRequest.getId());
        ModelMapper modelMapper = new ModelMapper();
        CallerEntity callerEntity = modelMapper.map(callerDto, CallerEntity.class);
        //        set offre is afected
        offres.forEach(el -> {
            el.set_affected_to_caller(true);
            el.setCaller(callerEntity);
            offreDao.save(el);
        });
        return modelMapper.map(callerEntity, CallerDto.class);
    }

    @Override
    public UserDto getByOffre(Integer id) {
        OffreEntity offreEntity = offreDao.findById(id).orElse(null);
        if (offreEntity == null) throw new OffreNotFoundException("Offre non trouver par l'id " + id);
        ModelMapper modelMapper = new ModelMapper();
        if (offreEntity.is_affected_to_caller()) {
            if (offreEntity.getCaller() != null) {
                return modelMapper.map(offreEntity.getCaller(), UserDto.class);
            } else {
                throw new UserNotFoundException("Caller non trouver !!");
            }
        } else if (offreEntity.is_affected_to_supervisor()) {
            if (offreEntity.getSupervisor() != null) {
                return modelMapper.map(offreEntity.getSupervisor(), UserDto.class);
            } else {
                throw new UserNotFoundException("Supervisor non trouver !!");
            }
        } else {
            throw new UserNotFoundException("Offre n'est pas affecter a aucun personne !!");
        }

    }

    @Override
    public List<OffreDto> getBySupervisor(Integer id) {
        SupervisorDto supervisorDto = supervisorService.findById(id);
        return supervisorDto.getOffres();
    }

    @Override
    public List<OffreDto> getByCaller(Integer id) {
        CallerDto callerDto = callerService.findById(id);
        return callerDto.getOffres();
    }

    List<OffreEntity> validateOffreRequest(List<Integer> ids, boolean to_supervisor) {
        List<OffreEntity> offres = ids.stream().map(el -> offreDao.findById(el).orElse(null)).collect(Collectors.toList());
        for (int i = 0; i < ids.size(); i++) {
            if (offres.get(i) == null) throw new OffreNotFoundException("Offre non trouver par l'id " + ids.get(i));
            if (to_supervisor) {
                if (offres.get(i).is_affected_to_supervisor()) {
                    throw new AlreadyAffectedException("offre avec l'id " + ids.get(i) + " est deja afecter");
                }
            } else {
                if (offres.get(i).is_affected_to_caller()) {
                    throw new AlreadyAffectedException("offre avec l'id " + ids.get(i) + " est deja afecter");
                }
            }
        }
        return offres;
    }
}
