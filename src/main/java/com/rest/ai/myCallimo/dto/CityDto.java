package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private Integer id;
    @NotBlank(message = "Ce champ est obligatoite !!")
    private String name;
//    @JsonIgnore
//    private List<OffreEntity> offres;
//    @JsonIgnore
//    private CallerEntity caller;
}
