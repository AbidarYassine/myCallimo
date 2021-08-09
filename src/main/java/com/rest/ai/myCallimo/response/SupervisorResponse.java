package com.rest.ai.myCallimo.response;

import com.rest.ai.myCallimo.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorResponse extends UserResponse {
    private List<CallerResponse> callers;
    private CityDto city;
//    private List<OffreDto> offres;
}
