package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "supervisors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupervisorEntity extends UserEntity {
    private String role = "SUPERVISOR";

    @ManyToOne
    private AdminEntity admin;


    @OneToMany(mappedBy = "supervisor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CallerEntity> callers;
}
