package com.rest.ai.myCallimo.dto;

import com.rest.ai.myCallimo.entities.OffreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreTypeDto {
    private Integer id;
    private String type;
    private List<OffreEntity> offres;
}
