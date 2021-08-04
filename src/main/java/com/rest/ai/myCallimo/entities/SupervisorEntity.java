package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity()
@Table(name = "supervisors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupervisorEntity extends UserEntity {
    private String role = "SUPERVISOR";

    @ManyToOne
    private AdminEntity admin;

//    https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api

    //    LAZY = fetch when needed  getCallers()
//    EAGER = fetch immediately
    @OneToMany(mappedBy = "supervisor")
    @LazyCollection(LazyCollectionOption.TRUE) // fetch = FetchType.EAGER.
    private List<CallerEntity> callers;
    @OneToMany(mappedBy = "supervisor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OffreEntity> offres;


}
