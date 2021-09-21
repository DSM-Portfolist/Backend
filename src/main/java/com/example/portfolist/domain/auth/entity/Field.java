package com.example.portfolist.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "field")
@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;

    @OneToOne
    @JoinColumn(name = "fieldKind_field")
    private FieldKind fieldKind;

}
