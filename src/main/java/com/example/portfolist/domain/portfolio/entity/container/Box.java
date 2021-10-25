package com.example.portfolist.domain.portfolio.entity.container;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE)
    private List<BoxImage> boxImageList;

    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE)
    private List<BoxText> boxTextList;

    public static Box of(Container container) {
        return Box.builder()
                .container(container)
                .build();
    }
}
