package com.example.portfolist.domain.portfolio.entity.container;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BoxText {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;
}
