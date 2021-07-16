package com.rest.ai.myCallimo.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceurBase implements Serializable {

    private String agencie_id;
    private String offer_agency_name;
    private String offer_telephone;
    private String offer_agency_email;  // agence pour l'affecter a l'annonce
    private String offer_agency_address;

}
