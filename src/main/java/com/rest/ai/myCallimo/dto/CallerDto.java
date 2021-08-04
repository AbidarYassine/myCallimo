package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallerDto extends UserDto {
    private String role = "CALLER";
    @NotNull(message = "Ce champ est obligatoite !!")
    private Integer city_id;
    private CityDto city;
    private SupervisorDto supervisor;
    private List<OffreDto> offres;
}
