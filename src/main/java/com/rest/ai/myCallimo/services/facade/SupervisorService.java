package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import org.springframework.data.repository.query.Param;


public interface SupervisorService {
    SupervisorDto save(SupervisorDto t, AdminDto admin);

    SupervisorDto findByEmail(String email);

    int delete(Integer id);

    SupervisorDto findById(Integer id);

    int update(SupervisorDto supervisorDto, Integer id);

    public void deleteAllByEmail(String email);

    int deleteById(int id);

}
