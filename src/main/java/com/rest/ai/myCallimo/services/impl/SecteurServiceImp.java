package com.rest.ai.myCallimo.services.impl;

import com.rest.ai.myCallimo.dao.SecteurDao;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.Secteur;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.SupervisorSecteurResponse;
import com.rest.ai.myCallimo.services.facade.SecteurService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecteurServiceImp implements SecteurService {
    private final SecteurDao secteurDao;
    private final ModelMapper modelMapper;

    public SecteurServiceImp(SecteurDao secteurDao, ModelMapper modelMapper) {
        this.secteurDao = secteurDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public SecteurDto save(SecteurDto secteurDto) {

        Secteur saved = secteurDao.save(modelMapper.map(secteurDto, Secteur.class));
        return modelMapper.map(saved, SecteurDto.class);
    }

    @Override
    public List<SecteurDto> findAll() {

        return secteurDao.findAll()
                .stream()
                .map(el -> modelMapper.map(el, SecteurDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SecteurDto findById(Integer id) {
        Secteur secteur = secteurDao.findById(id).orElse(null);
        if (secteur == null) throw new UserNotFoundException("Secteur non trouver ");

        return modelMapper.map(secteur, SecteurDto.class);
    }

    @Override
    public SecteurDto findByLibelle(String libelle) {
        Secteur secteur = secteurDao.findByLibelle(libelle);
        if (secteur == null) throw new UserNotFoundException("Secteur avec le nom  " + libelle + " non trouver !!");

        return modelMapper.map(secteur, SecteurDto.class);
    }

    @Override
    public SecteurDto findByCode(String code) {
        Secteur secteur = secteurDao.findByCode(code);
        if (secteur == null) throw new UserNotFoundException("Secteur avec le code  " + code + " non trouver !!");

        return modelMapper.map(secteur, SecteurDto.class);
    }

    @Override
    public List<CityDto> getBySecteurId(Integer id) {
        Secteur secteur = secteurDao.findById(id).orElse(null);
        if (secteur == null) throw new UserNotFoundException("Secteur non trouver par l'id " + id);

        return secteur.getCities().stream().map(el -> modelMapper.map(el, CityDto.class))
                .collect(Collectors.toList());

    }

    public SupervisorDto getBySecteurCode(String code) {
        Secteur secteur = secteurDao.findByCode(code);
        if (secteur == null) throw new UserNotFoundException("Secteur non trouver par le code  " + code);

        if (secteur.isAfected() && secteur.getSupervisor() != null) {
            return modelMapper.map(secteur.getSupervisor(), SupervisorDto.class);
        } else {
            return null;
        }

    }

    @Override
    public void updateSecteur() {
        secteurDao.findAll().forEach(el -> {
            if (el.getSupervisor() == null) {
                el.setAfected(false);
                el.setSupervisor(null);
                secteurDao.save(el);
            }
        });
    }

    @Override
    public List<SupervisorSecteurResponse> getBySecteurCodes(List<String> codes) {
        List<SupervisorSecteurResponse> supervisorSecteurResponses = new ArrayList<>();

        for (String c : codes) {
            SupervisorDto supervisorDto = getBySecteurCode(c);
            if (supervisorDto != null) {
                SupervisorSecteurResponse supervisorSecteurResponse = new SupervisorSecteurResponse();
                supervisorSecteurResponse.setSupervisor(modelMapper.map(supervisorDto, SupervisorResponse.class));
                supervisorSecteurResponse.setCode(c);
                supervisorSecteurResponses.add(supervisorSecteurResponse);
            }
        }
        return supervisorSecteurResponses;

    }

    @Override
    public List<SecteurDto> getAllSecteurs() {
        return secteurDao.getAllSecteurs()
                .stream()
                .map(el -> modelMapper.map(el, SecteurDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<SecteurDto> getSecteurNonAfecter() {
        return this.findAll().stream().filter(el -> !el.isAfected()).collect(Collectors.toList());
    }

    @Override
    public List<SecteurDto> getSecteurAfected() {
        return this.findAll().stream().filter(SecteurDto::isAfected).collect(Collectors.toList());
    }
}
