package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.OffreDao;
import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.OffreTypeDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.*;
import com.rest.ai.myCallimo.exception.NotFoundException;
import com.rest.ai.myCallimo.exception.offre.OffreNotFoundException;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.request.AffectationRequest;
import com.rest.ai.myCallimo.request.AppelDto;
import com.rest.ai.myCallimo.request.search.PagedResponse;
import com.rest.ai.myCallimo.request.search.SearchRequest;
import com.rest.ai.myCallimo.request.search.SearchRequestUtil;
import com.rest.ai.myCallimo.response.AppelResponse;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.services.facade.*;
import com.rest.ai.myCallimo.shared.EMail;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OffreServiceImpl implements OffreService {

    private final OffreDao offreDao;
    private final SupervisorService supervisorService;
    private final CallerService callerService;
    private final EmailSenderService emailService;
    private final CategoryService categoryService;
    private final OffreTypeService offreTypeService;
    private final ModelMapper modelMapper;

    public OffreServiceImpl(OffreDao offreDao, SupervisorService supervisorService, CallerService callerService, EmailSenderService service, ModelMapper modelMapper, CategoryService categoryService, OffreTypeService offreTypeService) {
        this.offreDao = offreDao;
        this.supervisorService = supervisorService;
        this.callerService = callerService;
        this.emailService = service;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.offreTypeService = offreTypeService;
    }

    @Override
    public OffreDto save(OffreDto offreDto) {
        OffreEntity offreEntity = offreDao.save(modelMapper.map(offreDto, OffreEntity.class));
        OffreEntity saved = offreDao.save(offreEntity);
        return modelMapper.map(saved, OffreDto.class);
    }

    @Override
    public int deleteCaller(Integer id) {
    
        CallerEntity callerEntity = modelMapper.map(callerService.findById(id), CallerEntity.class);
        if (callerEntity.getOffres().size() > 0) {
            callerEntity.getOffres().forEach(el -> {
//                no affectation to caller
                el.set_affected_to_caller(false);
//                assure no afectation to supervisor a verifier
                el.set_affected_to_supervisor(false);
                el.setCaller(null);
                offreDao.save(el);
            });
        }
        return callerService.deleteById(id);
    }

    @Override
    public List<OffreDto> findByIds(List<Integer> ids) {
        List<OffreEntity> offreEntities = offreDao.findAllById(ids);
    
        return offreEntities.stream().map(el -> modelMapper.map(el, OffreDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<OffreDto> findAll() {
        return offreDao.findAll().stream().map(el -> modelMapper.map(el, OffreDto.class)).collect(Collectors.toList());
    }

    @Override
    public PagedResponse<OffreDto> list(final SearchRequest request) {
        final Page<OffreEntity> response = offreDao.getAllOffre(SearchRequestUtil.toPageRequest(request));
        return returnResponse(response);
    }

    public PagedResponse<OffreDto> returnResponse(Page<OffreEntity> response) {
        if (response.isEmpty()) {
            return new PagedResponse<>(Collections.emptyList(), 0, response.getTotalElements());
        }
        List<OffreDto> dtos = response.getContent().stream()
                .map(el -> modelMapper.map(el, OffreDto.class))
                .collect(Collectors.toList());
        return new PagedResponse<>(dtos, dtos.size(), response.getTotalElements());
    }

    @Override
    public PagedResponse<OffreDto> listAfected(final SearchRequest request) {
        request.setSize(40);
        final Page<OffreEntity> response = offreDao.getOffresAfected(SearchRequestUtil.toPageRequest(request));
        return returnResponse(response);
    }

    @Override
    public CategoryDto updateOffreCategory(Integer id_offre, Integer id_category) {
        if (id_category != null) {
            OffreEntity offreEntity = offreDao.findById(id_offre).orElseThrow(() -> new NotFoundException("Offre Not Found"));
            CategoryDto categoryDto = categoryService.findById(id_category);
            CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
            offreEntity.setCategory(categoryEntity);
            offreDao.save(offreEntity);
            return categoryDto;
        }
        return null;

    }

    @Override
    public OffreTypeDto updateOffreType(Integer id_offre, Integer id_offre_type) {
        if (id_offre_type != null) {
            OffreEntity offreEntity = offreDao.findById(id_offre).orElseThrow(() -> new NotFoundException("Offre Not Found"));
            OffreTypeDto offreTypeDto = offreTypeService.findById(id_offre_type);
            OffreTypeEntity offreTypeEntity = modelMapper.map(offreTypeDto, OffreTypeEntity.class);
            offreEntity.setOffre_type(offreTypeEntity);
            offreDao.save(offreEntity);
            return offreTypeDto;
        }
        return null;
    }


    @Override
    public OffreDto findById(Integer id) {
        OffreEntity offreEntity = offreDao.findById(id).orElseThrow(() -> new NotFoundException("Offre not found"));
        return modelMapper.map(offreEntity, OffreDto.class);
    }

    @Override
    public OffreEntity findByIdE(Integer id) {
        OffreEntity offreEntity = offreDao.findById(id).orElseThrow(() -> new NotFoundException("Offre not found"));
        return offreEntity;
    }

    @Override
    public OffreEntity save(OffreEntity offreEntity) {
        return offreDao.save(offreEntity);
    }


    //    affectation des offres aux supervisor
    @Transactional
    @Override
    public String affecterOffreToSupervisor(AffectationRequest affectationRequest) {
        /*validate offres == serach by id test if offre already affected */
        List<OffreEntity> offres = validateOffreRequest(affectationRequest.getIds(), true);
        /* get supervisor */
        SupervisorResponse supervisorDto = supervisorService.findById(affectationRequest.getId());

        /*get entities */
        SupervisorEntity supervisorEntity = modelMapper.map(supervisorDto, SupervisorEntity.class);
        //        set offre is afected
        offres = offres.stream().peek(el -> {
            el.set_affected_to_supervisor(true);
            el.set_affected_to_caller(false);
            el.setSupervisor(supervisorEntity);
        }).collect(Collectors.toList());
        offreDao.saveAll(offres);
//        offres.forEach(el -> {
//            el.set_affected_to_supervisor(true);
//            el.set_affected_to_caller(false);
//            el.setSupervisor(supervisorEntity);
//            offreDao.save(el);
//        });
        EMail eMail = prepareEmail(supervisorEntity.getEmail(), offres.size(), "SUPERVISOR");
        emailService.sendEmail(eMail);
        return "done";
    }

    @Transactional
    @Override
    public String affecterOffreToCaller(AffectationRequest affectationRequest) {
        List<OffreEntity> offres = validateOffreRequest(affectationRequest.getIds(), false);
        CallerResponse callerResponse = callerService.findById(affectationRequest.getId());

        CallerEntity callerEntity = modelMapper.map(callerResponse, CallerEntity.class);
        //        set offre is afected
        offres.forEach(el -> {
            el.set_affected_to_caller(true);
            el.set_affected_to_supervisor(false);
            el.setCaller(callerEntity);
            offreDao.save(el);
        });

        EMail eMail = prepareEmail(callerEntity.getEmail(), offres.size(), "CALLER");
        emailService.sendEmail(eMail);
        return "done";
    }

    EMail prepareEmail(String to, Integer numOffre, String role) {
        EMail eMail = new EMail();
        eMail.setFrom("yassinabidar201@gmail.com");
        eMail.setSubject("Nouvelle offre 🙂🙂");
        Map<String, Object> model = new HashMap<>();
        model.put("nombre", numOffre);
        model.put("role", role);
        eMail.setData(model);
        eMail.setTo(to);
        return eMail;
    }

    @Override
    public UserDto getByOffre(Integer id) {
        OffreEntity offreEntity = offreDao.findById(id).orElse(null);
        if (offreEntity == null) throw new OffreNotFoundException("Offre non trouver par l'id " + id);
    
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


    List<OffreEntity> validateOffreRequest(List<Integer> ids, boolean to_supervisor) {
        List<OffreEntity> offres = ids.stream().map(el -> offreDao.findById(el).orElse(null)).collect(Collectors.toList());
//        for (int i = 0; i < ids.size(); i++) {
//            if (offres.get(i) == null) throw new OffreNotFoundException("Offre non trouver par l'id " + ids.get(i));
//            if (to_supervisor) {
//                if (offres.get(i).is_affected_to_supervisor()) {
//                    throw new AlreadyAffectedException("offre avec l'id " + ids.get(i) + " est deja afecter");
//                }
//            } else {
//                if (offres.get(i).is_affected_to_caller()) {
//                    throw new AlreadyAffectedException("offre avec l'id " + ids.get(i) + " est deja afecter");
//                }
//            }
//        }
        return offres;
    }


    //  private String title;// to_update
    @Override
    public OffreDto updateOffre(OffreDto offreDto) {
        OffreEntity offreEntity = offreDao.findById(offreDto.getId()).orElseThrow(() -> new NotFoundException("Offre Not Found"));
        if (StringUtils.hasLength(offreDto.getBathroom())) {
            offreEntity.setBathroom(offreDto.getBathroom());
        }
        if (StringUtils.hasLength(offreDto.getTitle())) {
            offreEntity.setBathroom(offreDto.getTitle());
        }
        if (StringUtils.hasLength(offreDto.getSurface_land())) {
            offreEntity.setSurface_land(offreDto.getSurface_land());
        }
        if (StringUtils.hasLength(offreDto.getOffer_latitude())) {
            offreEntity.setOffer_latitude(offreDto.getOffer_latitude());
        }
        if (StringUtils.hasLength(offreDto.getOffer_longitude())) {
            offreEntity.setOffer_longitude(offreDto.getOffer_longitude());
        }
        if (StringUtils.hasLength(offreDto.getArea())) {
            offreEntity.setArea(offreDto.getArea());
        }
        if (StringUtils.hasLength(offreDto.getDescription())) {
            offreEntity.setDescription(offreDto.getDescription());
        }
        if (StringUtils.hasLength(offreDto.getArea_units())) {
            offreEntity.setArea_units(offreDto.getArea_units());
        }
        if (StringUtils.hasLength(offreDto.getCurrency())) {
            offreEntity.setCurrency(offreDto.getCurrency());
        }
        if (StringUtils.hasLength(offreDto.getPrice())) {
            offreEntity.setPrice(offreDto.getPrice());
        }
        if (StringUtils.hasLength(offreDto.getLast_price())) {
            offreEntity.setLast_price(offreDto.getLast_price());
        }
        if (StringUtils.hasLength(offreDto.getFirst_price())) {
            offreEntity.setFirst_price(offreDto.getFirst_price());
        }
        if (StringUtils.hasLength(offreDto.getChamber())) {
            offreEntity.setChamber(offreDto.getChamber());
        }
        if (StringUtils.hasLength(offreDto.getPiscine())) {
            offreEntity.setPiscine(offreDto.getPiscine());
        }
        if (StringUtils.hasLength(offreDto.getPices())) {
            offreEntity.setPices(offreDto.getPices());
        }
        if (StringUtils.hasLength(offreDto.getIs_active())) {
            offreEntity.setPices(offreDto.getIs_active());
        }
        return modelMapper.map(offreDao.save(offreEntity), OffreDto.class);
    }

    @Override
    public AppelResponse saveWithSup(Integer id, AppelDto appelDto, Integer sup_id) {
        return null;

    }

    @Override
    public AppelResponse saveWithCaller(Integer id, AppelDto appelDto, Integer caller_id) {
        return null;
    }


}
