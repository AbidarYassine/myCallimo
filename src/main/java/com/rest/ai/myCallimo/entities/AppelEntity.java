package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "appeles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppelEntity extends AbstractEntity {

    @Column(nullable = false)
    private Instant date;
    @Column(nullable = false)
    private String duree;

    @ManyToOne
    @JoinColumn(name = "caller_id")
    private CallerEntity caller;

    @Enumerated(EnumType.STRING)
    private TypeAppel typeAppel;

    @OneToOne(mappedBy = "appel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private OffreEntity offre;

}
