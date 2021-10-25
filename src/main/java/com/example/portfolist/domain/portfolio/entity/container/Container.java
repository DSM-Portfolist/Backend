package com.example.portfolist.domain.portfolio.entity.container;

import com.example.portfolist.domain.portfolio.dto.request.ContainerRequest;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.container.Box;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Container {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 45)
    private String title;

    @OneToMany(mappedBy = "container", cascade = CascadeType.REMOVE)
    private List<Box> boxList;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public static Container of(ContainerRequest request, Portfolio portfolio) {
        return Container.builder()
                .title(request.getContainerTitle())
                .portfolio(portfolio)
                .build();
    }
}
