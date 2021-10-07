package com.example.portfolist.domain.portfolio.entity.touching;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "touching")
public class Touching {

    @EmbeddedId
    private TouchingId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "toucher_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("portfolioId")
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

}
