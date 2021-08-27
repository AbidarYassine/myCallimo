package com.rest.ai.myCallimo.dto;

import com.rest.ai.myCallimo.response.AppelResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffreDto implements Serializable {
    private Integer id; //
    private String url; //
    private String title;//
    private String reference;
    private String description;//
    private String address; //
    private String piscine; //
    private String pices; //
    private String is_active; //
    private String is_processed; //
    private String area; //
    private String area_units; //
    private String currency; //
    private String price; //
    private String last_price; //
    private String first_price; //
    private String chamber; //
    private String last_check_date; //
    private String is_new; //
    private String offer_status; //
    private String offer_code_status; //
    private String offer_latitude; //
    private String offer_longitude; //
    private String offer_rank_obtained; //
    private String offer_rank_max; //
    private String is_update_price; //
    private String is_update_area; //
    private String is_url_exist; //
    private String new_price_date; //
    private String department; //
    private String bathroom;//
    private String surface_land; //
    private String offer_dpe; //
    private String offer_ges;//
    private boolean is_affected_to_supervisor;
    private boolean is_affected_to_caller;
    private String lib_sector;
    private String zip_sector;
    private AnnonceurDto annonceur;

    private CategoryDto category;

    private OffreTypeDto offre_type;
    private CityDto city;

    private AppelResponse appel;
}
