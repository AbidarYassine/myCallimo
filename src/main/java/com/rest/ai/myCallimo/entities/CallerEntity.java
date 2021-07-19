package com.rest.ai.myCallimo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "callers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CallerEntity extends UserEntity {
    private String role = "CALLER";

    @ManyToOne
    private SupervisorEntity supervisor;

    @OneToOne(mappedBy = "caller", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CityEntity city;

    @OneToMany(mappedBy = "caller", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AppelEntity> appeles;


}
