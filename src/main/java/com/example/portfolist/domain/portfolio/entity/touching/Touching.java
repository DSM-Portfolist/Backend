package com.example.portfolist.domain.portfolio.entity.touching;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
