package com.example.portfolist.domain.portfolio.entity.touching;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(TouchingId.class)
@Entity
public class Touching {

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    @Id
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "toucher_id", nullable = false)
    @Id
    private User user;
}
