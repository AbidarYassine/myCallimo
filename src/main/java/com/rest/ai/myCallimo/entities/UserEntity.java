package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
public class UserEntity extends AbstractEntity {
    private static final long serialVersionUID = -1100455345047414888L;


    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;


    private String telephone;

    @Column(nullable = false)
    private String password;

    private boolean passwordChanged = false;

    private String avatar = "https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_960_720.png";
    private String role;


}
