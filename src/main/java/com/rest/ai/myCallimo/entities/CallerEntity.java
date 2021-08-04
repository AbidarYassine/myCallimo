package com.rest.ai.myCallimo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "callers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CallerEntity extends UserEntity {
    private String role = "CALLER";

    @ManyToOne
    private SupervisorEntity supervisor;


    @OneToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @OneToMany(mappedBy = "caller", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppelEntity> appeles;

    @OneToMany(mappedBy = "caller")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OffreEntity> offres;


}
