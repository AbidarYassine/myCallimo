package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallerDto extends UserDto {
    private String role = "CALLER";
    private Integer city_id;
    private CityDto city;
    private SupervisorDto supervisor;
}
