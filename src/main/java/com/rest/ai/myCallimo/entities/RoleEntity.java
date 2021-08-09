package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
}
