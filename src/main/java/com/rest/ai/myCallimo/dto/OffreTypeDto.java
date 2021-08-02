package com.rest.ai.myCallimo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.ai.myCallimo.entities.OffreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreTypeDto {
    private Integer id;
    @NotBlank(message = "ce champ est obligatoire !!")
    private String type;
    @JsonIgnore
    private List<OffreEntity> offres;
}
