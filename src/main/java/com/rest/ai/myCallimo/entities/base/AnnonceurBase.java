package com.rest.ai.myCallimo.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnnonceurBase implements Serializable {

    private String agencie_id;
    private String offer_agency_name;  // pas unique
    private String offer_telephone;  // unique
    private String offer_agency_email;  // agence pour l'affecter a l'annonce
    private String offer_agency_address;

}
