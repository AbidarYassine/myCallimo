package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
