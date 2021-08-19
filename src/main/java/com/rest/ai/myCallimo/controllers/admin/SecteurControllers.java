package com.rest.ai.myCallimo.controllers.admin;

import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.request.GetSupervisorByCodesRequest;
import com.rest.ai.myCallimo.request.search.PagedResponse;
import com.rest.ai.myCallimo.request.search.SearchRequest;
import com.rest.ai.myCallimo.response.CityResponse;
import com.rest.ai.myCallimo.response.SecteurResponse;
import com.rest.ai.myCallimo.response.SupervisorSecteurResponse;
import com.rest.ai.myCallimo.services.facade.SecteurService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j

@RequestMapping("/admin/secteurs")
public class SecteurControllers {

    private final SecteurService secteurService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public SecteurControllers(SecteurService secteurService, ModelMapper modelMapper, UserService userService) {
        this.secteurService = secteurService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<SecteurResponse> save(@Valid @RequestBody() SecteurDto secteurDto) {
        SecteurDto dto = secteurService.save(secteurDto);
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

//    @GetMapping("")
//    public ResponseEntity<List<SecteurResponse>> findAll() {
//        List<SecteurResponse> secteurResponses =
//                secteurService.findAll().stream()
//                        .map(el -> modelMapper.map(el, SecteurResponse.class))
//                        .collect(Collectors.toList());
//        return new ResponseEntity<>(secteurResponses, HttpStatus.OK);
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<SecteurResponse> findById(@PathVariable() Integer id) {
        SecteurDto dto = secteurService.findById(id);
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/libelle/{libelle}")
    public ResponseEntity<SecteurResponse> findByLibelle(@PathVariable() String libelle) {
        SecteurDto dto = secteurService.findByLibelle(libelle);
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/code/{code}")
    public ResponseEntity<SecteurResponse> findByCode(@PathVariable() String code) {
        SecteurDto dto = secteurService.findByCode(code);
        return new ResponseEntity<>(modelMapper.map(dto, SecteurResponse.class), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/cities/secteur/{id}")
    public ResponseEntity<List<CityDto>> getBySecteurId(@PathVariable() Integer id) {
        List<CityDto> list = secteurService.getBySecteurId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/non-afected")
    public ResponseEntity<List<SecteurResponse>> getSecteurNonAfecter() {
        return new ResponseEntity<>(secteurService.getSecteurNonAfecter(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERVISOR') or hasRole('CALLER')")
    @GetMapping("/afected")
    public ResponseEntity<List<SecteurResponse>> getSecteurAfected() {
        List<SecteurResponse> secteurResponses =
                secteurService.getSecteurAfected().stream()
                        .map(el -> modelMapper.map(el, SecteurResponse.class))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(secteurResponses, HttpStatus.OK);
    }

    @GetMapping("/update")
    public int update() {
        secteurService.updateSecteur();
        return 1;
    }

    @PostMapping("/supervisors/codes")
    public ResponseEntity<List<SupervisorSecteurResponse>> getBySecteurCodes(@RequestBody() GetSupervisorByCodesRequest getSupervisorByCodesRequest) {
        return new ResponseEntity<>(secteurService.getBySecteurCodes(getSupervisorByCodesRequest.getCodes()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/user-id/{id}")
    public List<SecteurResponse> findSecteursBySupId(@PathVariable() Integer id) {
        return userService.findSecteursBySupId(id);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/cities/user-id/{id}")
    public List<CityResponse> findCitiesBySubId(@PathVariable() Integer id) {
        return userService.findCitiessBySubId(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<SecteurResponse> list(SearchRequest request) {
        request.setSize(20);
        return secteurService.list(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/affecter/{sup_id}/{secteur_id}")
    public ResponseEntity<String> affecterSupToSecteur(@PathVariable Integer sup_id, @PathVariable Integer secteur_id) {
        return new ResponseEntity<>(secteurService.affecterSupToSecteur(sup_id, secteur_id), HttpStatus.OK);
    }
}
