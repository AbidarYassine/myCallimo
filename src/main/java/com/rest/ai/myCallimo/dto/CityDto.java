package com.rest.ai.myCallimo.dto;

import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private Integer id;
    private String name;
    private List<OffreEntity> offres;
    private CallerEntity caller;
}
