package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;

import java.util.List;

public interface CallerService {
    CallerDto findByEmail(String email);

    CallerDto save(CallerDto callerDto, SupervisorDto supervisorDto);

    List<CallerDto> getBySupervisorId(Integer id);
}
