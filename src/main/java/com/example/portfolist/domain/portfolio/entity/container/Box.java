package com.example.portfolist.domain.portfolio.entity.container;

import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @OneToMany(mappedBy = "box")
    private List<BoxImage> boxImageList;

    @OneToMany(mappedBy = "box")
    private List<BoxText> boxTextList;
}
