package com.rest.ai.myCallimo.services.facade;


import com.rest.ai.myCallimo.dto.AdminDto;
import com.rest.ai.myCallimo.dto.SupervisorDto;



public interface SupervisorService  {
   SupervisorDto save(SupervisorDto t, AdminDto admin);
   SupervisorDto findByEmail(String email);
}
