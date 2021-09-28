package com.example.portfolist.domain.portfolio.entity.portfolio;

import com.example.portfolist.domain.auth.entity.FieldKind;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PortfolioField {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "kind", nullable = false)
    private FieldKind fieldKind;
}
