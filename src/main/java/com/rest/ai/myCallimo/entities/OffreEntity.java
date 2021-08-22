package com.rest.ai.myCallimo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(name = "offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OffreEntity implements Serializable {

    private static final long serialVersionUID = 6659221496655031009L;
    @Id
    @GeneratedValue
    private Integer id; //
    private String url; //
    private String title;//
    private String reference;
    @Column(length = 5000)
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

//    public boolean isIs_affected() {
//        return is_affected;
//    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonceur_id")
    private AnnonceurEntity annonceur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_type_id")
    private OffreTypeEntity offre_type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appel_id")
    private AppelEntity appel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private SupervisorEntity supervisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caller_id", nullable = true)
    private CallerEntity caller;
}
