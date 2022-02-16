package com.example.portfolist.domain.portfolio.entity.container;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ContainerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @Column(length = 100, nullable = false)
    private String url;

    public static ContainerImage toEntity(Container container, String boxImgName) {
        return ContainerImage.builder()
                .container(container)
                .url(boxImgName)
                .build();
    }
}
