package com.example.portfolist.domain.portfolio.entity;

import com.example.portfolist.domain.portfolio.dto.request.MoreInfoRequest;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MoreInfo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(length = 10)
    private String name;

    @Column(length = 45)
    private String content;

    public static MoreInfo toEntity(Portfolio portfolio, MoreInfoRequest moreInfoRequest) {
        return MoreInfo.builder()
                .name(moreInfoRequest.getName())
                .content(moreInfoRequest.getContent())
                .portfolio(portfolio)
                .build();
    }
}
