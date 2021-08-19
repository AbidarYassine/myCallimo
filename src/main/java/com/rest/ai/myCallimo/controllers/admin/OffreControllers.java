package com.rest.ai.myCallimo.controllers.admin;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.OffreDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.request.AffectationRequest;
import com.rest.ai.myCallimo.request.GetByIdsRequest;
import com.rest.ai.myCallimo.request.search.PagedResponse;
import com.rest.ai.myCallimo.request.search.SearchRequest;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.SupervisorResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.OffreService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/admin/offres")
@RestController
@Slf4j
public class OffreControllers {
    private final OffreService offreService;
    private final UserService userService;

    @Autowired
    public OffreControllers(OffreService offreService, UserService userService) {
        this.offreService = offreService;
        this.userService = userService;
    }

    @PostMapping("/affectation-supervisor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SupervisorResponse> affecterOffreToSupervisor(@Valid @RequestBody() AffectationRequest affectationRequest) {
        SupervisorDto supervisorDto = offreService.affecterOffreToSupervisor(affectationRequest);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(supervisorDto, SupervisorResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/user-id/{id}")
    public PagedResponse<OffreDto> findByUserId(@PathVariable() Integer id, SearchRequest request) {
        List<OffreDto> res = userService.findByUserId(id);
        if (res == null) return this.getOffres(request);
        PagedResponse<OffreDto> pagedResponse = new PagedResponse<OffreDto>();
        pagedResponse.setContent(res);
        pagedResponse.setCount(res.size());
        return pagedResponse;
    }

    @PostMapping("/affectation-caller")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    public ResponseEntity<CallerResponse> affecterOffreToCaller(@Valid @RequestBody() AffectationRequest affectationRequest) {
        CallerDto callerDto = offreService.affecterOffreToCaller(affectationRequest);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(callerDto, CallerResponse.class), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public OffreDto save(OffreDto offreDto) {
        return offreService.save(offreDto);
    }

    //    get all offre with annonceur contain telephone
    @GetMapping
    public List<OffreDto> findAll() {
        return offreService.findAll().stream()
                .filter
                        (el -> el.getAnnonceur() != null
                                && el.getAnnonceur().getTelephone() != null
                                && !el.getAnnonceur().getTelephone().equals("")
                                && !el.is_affected_to_caller()
                                && !el.is_affected_to_supervisor())
                .limit(50)
                .collect(Collectors.toList());
    }

    @GetMapping("/affected")
    public List<OffreDto> getOffreAfected() {
        return offreService.findAll().stream()
                .filter
                        (el -> el.getAnnonceur() != null
                                && el.getAnnonceur().getTelephone() != null
                                && !el.getAnnonceur().getTelephone().equals("")
                                && el.is_affected_to_caller()
                                || el.is_affected_to_supervisor())
                .collect(Collectors.toList());
    }

    //    find By Id
    @GetMapping("/id/{id}")
    public OffreDto findById(@PathVariable("id") Integer id) {
        return offreService.findById(id);
    }


    //    get offre affected to supervisor
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/supervisor/{id}")
    public List<OffreDto> getBySupervisor(@PathVariable() Integer id) {
        return offreService.getBySupervisor(id);
    }

    //    get offre affected to callers
    @PreAuthorize("hasRole('ADMIN') or hasRole('CALLER')")
    @GetMapping("/caller/{id}")
    public List<OffreDto> getByCaller(@PathVariable() Integer id) {
        return offreService.getByCaller(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR')")
    @GetMapping("/offre/{id}")
    public ResponseEntity<UserResponse> getByOffre(@PathVariable() Integer id) {
        UserDto userDto = offreService.getByOffre(id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(userDto, UserResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/delete-caller/{id}")
    public int deleteCaller(@PathVariable("id") Integer id) {
        return offreService.deleteCaller(id);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @PostMapping("/ids")
    public List<OffreDto> findByIds(@RequestBody() GetByIdsRequest getByIdsRequest) {
        return offreService.findByIds(getByIdsRequest.getIds());
    }

    @GetMapping("/offres")
    public PagedResponse<OffreDto> getOffres(SearchRequest request) {
        return offreService.list(request);
    }
}

