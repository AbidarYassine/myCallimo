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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "categories")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<OffreEntity> offres;
}
