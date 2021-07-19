package com.rest.ai.myCallimo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// c'est un Listener pour les champ created_at updated_at
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @CreatedDate
    @Column(nullable = true)
    @JsonIgnore
    private Instant created_at;

    @LastModifiedDate
    @Column(nullable = true)
    @JsonIgnore
    private Instant updated_at;

}
