package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecteurDto {
    private Integer id;
    @NotBlank
    private String libelle;
    @NotBlank
    private String code;
    private boolean afected;
    private List<CityDto> cities;
//    private SupervisorDto supervisor;
}
