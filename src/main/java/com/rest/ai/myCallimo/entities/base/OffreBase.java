package com.rest.ai.myCallimo.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OffreBase implements Serializable {

    //    Particiliers
    private static final long serialVersionUID = 4685133920522631453L;
    @Id
    @GeneratedValue
    @Column(name = "offer_id")
    private String offer_id;
    //    private String bienici_id;
    private String offer_url;
    private String offer_title;
    private String offer_reference;
    private String offer_description;
    private String offer_address;
    private String offer_piscine;
    private String offer_pices;
    private String is_active;
    private String is_processed;
    private String offer_area;
    private String offer_area_units;
    private String offer_currency;
    private String offer_price;
    private String offer_last_price;
    private String offer_first_price;
    private String offer_chamber;
    private String last_check_date;
//42

    //    info Annonceur
    private String agencie_id;
    private String offer_agency_name;
    private String offer_telephone;
    private String offer_agency_email;  // agence pour l'affecter a l'annonce
    private String offer_agency_address;


    //    Type
    private String offer_type;
    //    Category
    private String offer_category;
    //    City
    private String offer_city;

    private String is_new;
    private String offer_status;
    private String offer_code_status;
    private String offer_latitude;
    private String offer_longitude;
    private String offer_rank_obtained;
    private String offer_rank_max;
    private String is_update_price;
    private String is_update_area;
    private String is_url_exist;
    private String new_price_date;
    private String department;
    private String bathroom;
    private String surface_land;
    private String offer_dpe;
    private String offer_ges;
//    private String sites_sources;
//    private String selogger_site;
//    private String boncoin_site;

//    private String logi_site;
//    private String rankimo;
//    private String chassimo;
//    private String sources_sites;
//
//    private String from_site;
//    private String sources_site;
//    private String is_similaire;
//    private String similaire_id;
//    private String created_at;
//    private String updated_at;
    //    private String offer_agency_image;
//    private String offer_agency_siret; // système d’identification du répertoire des établissements
//    private String offer_agency_siren; // système d’identification du répertoire des entreprises
//private String image_type;

}
