package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorDto extends UserDto {
    private String role = "SUPERVISOR";
    private List<OffreDto> offres;
    private List<CallerDto> callers;
    private AdminDto admin;
    private List<SecteurDto> secteurs;

}
