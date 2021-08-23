package com.rest.ai.myCallimo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "appeles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppelEntity extends AbstractEntity {

    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String duree;

    @ManyToOne
    @JoinColumn(name = "caller_id")
    private CallerEntity caller;

    //    @Enumerated(EnumType.STRING)
    private String typeAppel;

    @OneToOne(mappedBy = "appel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private OffreEntity offre;


    @ManyToOne()
    @JoinColumn(name = "supervisor_id")
    private SupervisorEntity supervisor;


}
