package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -1100455345047414888L;
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 60, unique = true)
    private String userId;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private boolean passwordChanged = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roles_id")
    private RoleEntity role;


}
