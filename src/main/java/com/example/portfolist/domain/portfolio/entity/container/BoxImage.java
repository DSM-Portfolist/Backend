package com.example.portfolist.domain.portfolio.entity.container;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BoxImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(length = 100, nullable = false)
    private String url;
}
