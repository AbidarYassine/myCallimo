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
    private AdminDto admin;
    private Integer city_id;
    private CityDto city;
}
