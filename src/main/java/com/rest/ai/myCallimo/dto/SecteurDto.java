package com.rest.ai.myCallimo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private SupervisorDto supervisor;
}
