package com.rest.ai.myCallimo.controllers.caller;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.response.CallerResponse;
import com.rest.ai.myCallimo.response.UserResponse;
import com.rest.ai.myCallimo.services.facade.AuthRoleService;
import com.rest.ai.myCallimo.services.facade.CallerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin/callers")
@CrossOrigin("*")
public class CallerController {

    private final CallerService callerService;
    private final AuthRoleService authRoleService;

    public CallerController(CallerService callerService, AuthRoleService authRoleService) {
        this.callerService = callerService;
        this.authRoleService = authRoleService;
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable() String email) {
        CallerDto callerDto = callerService.findByEmail(email);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(callerDto, CallerResponse.class), HttpStatus.OK);
    }

    @PostMapping("/supervisor-id/{supervisor_id}")
    public ResponseEntity<CallerResponse> save(@RequestBody() CallerDto callerDto, @PathVariable() Integer supervisor_id) {
        isAuthorized();
        CallerDto callerDtoResp = callerService.save(callerDto, supervisor_id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(callerDtoResp, CallerResponse.class), HttpStatus.CREATED);

    }

    @GetMapping("/supervisor-id/{id}")
    public ResponseEntity<List<CallerResponse>> getBySupervisorId(@PathVariable("id") Integer id) {
        isAuthorized();
        List<CallerDto> callerDtos = callerService.getBySupervisorId(id);
        if (callerDtos == null) throw new UserNotFoundException("superviseur non trouver par id " + id);
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(callerDtos.stream().map(el -> modelMapper.map(el, CallerResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public int retireCaller(@PathVariable("id") Integer id) {
        return callerService.deleteById(id);
    }

    private void isAuthorized() {
        if (!authRoleService.isAuthorized("ADMIN"))
            throw new UnAuthorizationUser("Access Denied");
    }
// TODO   Complete This
}
