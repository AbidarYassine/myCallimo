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
@Entity(name = "offers_type")
public class OffreTypeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String type;
    @OneToMany(mappedBy = "offre_type")
    private List<OffreEntity> offres;

}
