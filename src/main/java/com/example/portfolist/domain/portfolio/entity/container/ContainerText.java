package com.example.portfolist.domain.portfolio.entity.container;

import com.example.portfolist.domain.portfolio.dto.request.ContainerTextRequest;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ContainerText {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    public static ContainerText toEntity(Container container, ContainerTextRequest boxRequest) {
        return ContainerText.builder()
                .container(container)
                .title(boxRequest.getBoxTitle())
                .content(boxRequest.getBoxContent())
                .build();
    }
}
