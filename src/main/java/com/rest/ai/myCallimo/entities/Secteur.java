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

    @OneToMany(mappedBy = "secteur", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CityEntity> cities;

}
