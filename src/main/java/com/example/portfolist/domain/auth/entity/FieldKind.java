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
@Table(name = "field_kind")
@Entity
public class FieldKind {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int field;

    @Column(name = "content", nullable = false)
    private String content;

}
