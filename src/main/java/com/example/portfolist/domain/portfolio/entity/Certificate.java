package com.example.portfolist.domain.portfolio.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(length = 45)
    private String content;

    public static Certificate of(Portfolio portfolio, String certificate) {
        return Certificate.builder()
                .portfolio(portfolio)
                .content(certificate)
                .build();
    }
}
