package com.rest.ai.myCallimo.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityBase implements Serializable {

    private String offer_city;
    private List<CityApi> cities;
    private String input;
}
