package com.example.portfolist.domain.portfolio.entity;

import com.example.portfolist.domain.auth.entity.FieldKind;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public static PortfolioField of(Portfolio portfolio, FieldKind fieldKind) {
        return PortfolioField.builder()
                .portfolio(portfolio)
                .fieldKind(fieldKind)
                .build();
    }
}
