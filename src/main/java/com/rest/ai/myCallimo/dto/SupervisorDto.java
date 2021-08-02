package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorDto extends UserDto {
    private String role = "SUPERVISOR";
    private List<OffreDto> offres;
    private AdminDto admin;
    @NotNull(message = "ce champ est obligatoire !!")
    private Integer city_id;
    private CityDto city;
}
