package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "cities")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "city")
    private List<OffreEntity> offres;

    @OneToOne(mappedBy = "city", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CallerEntity caller;

}
