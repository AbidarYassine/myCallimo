package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "secteurs")
public class Secteur extends AbstractEntity implements Serializable {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private String code;

    private boolean afected = false;

    @OneToMany(targetEntity = CityEntity.class, mappedBy = "secteur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CityEntity> cities;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SupervisorEntity.class)
    @JoinColumn(name = "supervisor_id")
    private SupervisorEntity supervisor;

}
