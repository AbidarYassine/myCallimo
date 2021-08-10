package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dao.OffreDao;
import com.rest.ai.myCallimo.dao.SupervisorDao;
import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.Secteur;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.CityService;
import com.rest.ai.myCallimo.services.facade.SecteurService;
import com.rest.ai.myCallimo.services.facade.SupervisorService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class SupervisorServiceImpl implements SupervisorService {


    private final SupervisorDao supervisorDao;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CallerDao callerDao;
    private final OffreDao offreDao;
    private final SecteurService secteurService;

    @Autowired
    public SupervisorServiceImpl(SupervisorDao supervisorDao, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, CallerDao callerDao, OffreDao offreDao, SecteurService secteurService) {
        this.supervisorDao = supervisorDao;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.callerDao = callerDao;
        this.offreDao = offreDao;
        this.secteurService = secteurService;
    }


    @Override
    public SupervisorDto findByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        if (email.isEmpty() || email.isBlank()) {
            return null;
        }
        SupervisorEntity supervisorEntity = supervisorDao.findByEmail(email);
        return modelMapper.map(supervisorEntity, SupervisorDto.class);
    }

    @Transactional
    @Override
    public int delete(Integer id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).get();
        supervisorDao.delete(supervisorEntity);
        log.info("wsal hna ...");
        return 1;

    }

    @Override
    public SupervisorDto findById(Integer id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).orElse(null);
        if (supervisorEntity == null) throw new UserNotFoundException("Superviseur non trouver par l'id " + id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(supervisorEntity, SupervisorDto.class);
    }

    @Override
    public int update(SupervisorDto supervisorDto, Integer id) {
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).orElse(null);
        if (supervisorEntity == null) return -1;
        if (userService.findByEmail(supervisorDto.getEmail()) != null && !supervisorEntity.getEmail().equals(supervisorDto.getEmail()))
            return -2;
        ModelMapper modelMapper = new ModelMapper();
        String password;
        if (supervisorDto.getPassword() == null || supervisorDto.getPassword().isEmpty()) {
            password = supervisorEntity.getPassword();
        } else {
            password = bCryptPasswordEncoder.encode(supervisorDto.getPassword());
        }
        supervisorDto.setId(supervisorEntity.getId());
        supervisorEntity = modelMapper.map(supervisorDto, SupervisorEntity.class);
        supervisorEntity.setPassword(password);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AdminEntity adminEntity = modelMapper.map(userService.findByEmail(userName), AdminEntity.class);
        supervisorEntity.setAdmin(adminEntity);
        supervisorDao.save(supervisorEntity);
        return 1;
    }

    @Override
    public void deleteAllByEmail(String email) {
//        SupervisorEntity supervisorEntity = supervisorDao.findByEmail(email);
        supervisorDao.deleteAllByEmail(email);
    }

    @Transactional
    @Override
    public int deleteById(int id) {
//        set callers null
//        set offres null
//        delete supervisor
        SupervisorEntity supervisorEntity = supervisorDao.findById(id).orElse(null);
        if (supervisorEntity == null) throw new UserNotFoundException("Superviseur non trouver par l'id " + id);
        ModelMapper modelMapper = new ModelMapper();
        if (supervisorEntity.getCallers().size() > 0) {
            supervisorEntity.getCallers().forEach(el -> {
                el.setSupervisor(null);
                callerDao.save(el);
            });
        }
        if (supervisorEntity.getOffres().size() > 0) {
            supervisorEntity.getOffres().forEach(el -> {
                el.setSupervisor(null);
                el.set_affected_to_supervisor(false);
                el.set_affected_to_caller(false);
                offreDao.save(el);
            });
        }
        supervisorDao.deleteById(id);
        return 1;
    }

    @Override
    public SecteurDto affecterSupToSecteur(Integer sup_id, Integer secteur_id) {
        /* find dto by ids */
        SupervisorDto supervisorDto = findById(sup_id);
        SecteurDto dto = secteurService.findById(secteur_id);
        /* secteur belong to one supervisor */
        if (dto.getSupervisor() != null)
            throw new UserAlreadyExist("ce secteur " + dto.getLibelle() + " est deja affecté");
        /* get entities from dto*/
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(supervisorDto, SupervisorEntity.class);
        Secteur secteur = modelMapper.map(dto, Secteur.class);
        /* associate secteur with supervisor */
        secteur.setSupervisor(supervisorEntity);
        /* get dto to save */
        SecteurDto toSave = modelMapper.map(secteur, SecteurDto.class);
        SecteurDto saved = secteurService.save(toSave);
        return modelMapper.map(saved, SecteurDto.class);
    }

//    @Override
//    public SupervisorDto getByCity(Integer id) {
//        CityDto cityDto = cityService.findById(id);
//        if (cityDto == null) throw new UserNotFoundException("ville non trouver !!");
//        log.info("city is found ", cityDto);
//        SecteurDto dto = cityDto.getSecteur();
//        log.info("secteur is found ", dto);
//        if (dto == null) throw new UserNotFoundException("secteur non trouver !!");
//        if (dto.getSupervisor() == null) throw new UserNotFoundException("secteur non affecter !!");
//        return dto.getSupervisor();
//    }


    @Override
    public SupervisorDto save(SupervisorDto t, AdminDto admin) {
        if (userService.findByEmail(t.getEmail()) != null) {
            throw new UserAlreadyExist("user with this email " + t.getEmail() + " already exists");
        }
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(t, SupervisorEntity.class);
        supervisorEntity.setRole("SUPERVISOR");
        AdminEntity adminEntity = modelMapper.map(admin, AdminEntity.class);
        supervisorEntity.setAdmin(adminEntity);
        supervisorEntity.setPassword(bCryptPasswordEncoder.encode(t.getPassword()));
        SupervisorEntity supervisor = supervisorDao.save(supervisorEntity);
        return modelMapper.map(supervisor, SupervisorDto.class);
    }

    @Override
    public SupervisorDto save(SupervisorDto t) {
        ModelMapper modelMapper = new ModelMapper();
        SupervisorEntity supervisorEntity = modelMapper.map(t, SupervisorEntity.class);
        SupervisorEntity saved = supervisorDao.save(supervisorEntity);
        return modelMapper.map(saved, SupervisorDto.class);
    }
}
