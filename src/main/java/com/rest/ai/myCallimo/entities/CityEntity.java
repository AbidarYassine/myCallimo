package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private String name;
    @OneToMany(mappedBy = "city")
    private List<OffreEntity> offres;

}
