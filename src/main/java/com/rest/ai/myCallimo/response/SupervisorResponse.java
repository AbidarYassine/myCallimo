package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorResponse extends UserResponse {
    private List<CallerResponse> callers;
//    private List<SecteurDto> secteurs;
//    private List<OffreDto> offres;
}
