package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "supervisors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupervisorEntity extends UserEntity {
    @ManyToOne
    private AdminEntity admin;

    /*https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api/*

    /* LAZY = fetch when needed  getCallers() */
    /*   EAGER = fetch immediately */
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CallerEntity> callers;


    @OneToMany(mappedBy = "supervisor", fetch = FetchType.LAZY)
    private List<OffreEntity> offres;
    private String role = "SUPERVISOR";

    @OneToMany(mappedBy = "supervisor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Secteur> secteurs;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<AppelEntity> appel;


}
