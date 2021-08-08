package com.rest.ai.myCallimo.services.facade;

import com.rest.ai.myCallimo.dto.CallerDto;

import java.util.List;

public interface CallerService {
    CallerDto findByEmail(String email);

    CallerDto findById(Integer id);

    CallerDto save(CallerDto callerDto, Integer supervisor_id);

    CallerDto save(CallerDto callerDto);

    List<CallerDto> getBySupervisorId(Integer id);

    List<CallerDto> getAll();

    int deleteById(int id);

//    int retireCaller(Integer id);
}
