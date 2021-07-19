package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "admins")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AdminEntity extends UserEntity {
    private String role = "ADMIN";

    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SupervisorEntity> supervisors;

}
