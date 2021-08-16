package com.rest.ai.myCallimo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String code_postal;
    //    @JsonIgnore
//    private SecteurDto secteur;
//    @JsonIgnore
//    private List<OffreEntity> offres;
//    @JsonIgnore
//    private CallerDto caller;
}
