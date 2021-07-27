package com.rest.ai.myCallimo.controllers.caller;


import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.CallerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/caller")
@CrossOrigin("*")
public class CallerController {

    private final CallerService callerService;

    public CallerController(CallerService callerService) {
        this.callerService = callerService;
    }

    public CallerDto findByEmail(String email) {
        return callerService.findByEmail(email);
    }

    public CallerDto save(CallerDto callerDto, SupervisorDto supervisorDto) {
        return callerService.save(callerDto, supervisorDto);
    }

    @GetMapping("/supervisor-id/{id}")
    public List<CallerDto> getBySupervisorId(@PathVariable("id") Integer id) {
        List<CallerDto> callerDtos = callerService.getBySupervisorId(id);
        if (callerDtos == null) throw new UserNotFoundException("superviseur non trouver par id " + id);
        return callerDtos;
    }
// TODO   Complete This
}
