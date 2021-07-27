package com.rest.ai.myCallimo.controllers.admin;

import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cities")
@CrossOrigin("*")
@Slf4j
public class CityControllers {
    private final AuthRoleService authRoleService;
    private final CityService cityService;

    public CityControllers(AuthRoleService authRoleService, CityService cityService) {
        this.authRoleService = authRoleService;
        this.cityService = cityService;
    }

    @PostMapping("/")
    public CityDto save(@RequestBody() CityDto cityDto) {
        isAuthorized();
        return cityService.save(cityDto);
    }

    @GetMapping("/")
    public List<CityDto> findAll() {
        isAuthorized();
        return cityService.findAll();
    }

    @GetMapping("/id/{id}")
    public CityDto findById(@PathVariable("id") Integer id) {
        isAuthorized();
        CityDto cityDto = cityService.findById(id);
        if (cityDto == null) throw new CityNotFoundException("City not found by Id " + id);
        return cityDto;
    }

    @GetMapping("/name/{name}")
    public CityDto findByName(@PathVariable("name") String name) {
        isAuthorized();
        CityDto cityDto = cityService.findByName(name);
        if (cityDto == null) throw new CityNotFoundException("City not found by Id " + name);
        return cityDto;
    }

    @DeleteMapping("/id/{id}")
    public int delete(@PathVariable("id") Integer id) {
        isAuthorized();
        return cityService.delete(id);
    }

    private void isAuthorized() {
        if (!authRoleService.isAuthorized("ADMIN"))
            throw new UnAuthorizationUser("Access Denied");
    }
}



