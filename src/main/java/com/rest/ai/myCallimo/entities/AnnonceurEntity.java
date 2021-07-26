package com.rest.ai.myCallimo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "annonceurs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceurEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String annonceur_id;
    private String name;
    @Column(unique = true)
    private String telephone;
    private String email;
    private String address;

    @OneToMany(mappedBy = "annonceur")
    private List<OffreEntity> offres;
}
