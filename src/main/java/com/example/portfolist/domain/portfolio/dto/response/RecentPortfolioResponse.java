package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentPortfolioResponse {

    private long portfolioId;

    private String title;

    private String introduce;

    private String thumbnail;

    public static RecentPortfolioResponse of(Portfolio portfolio) {
        return RecentPortfolioResponse.builder()
                .portfolioId(portfolio.getPk())
                .title(portfolio.getTitle())
                .introduce(portfolio.getIntroduce())
                .thumbnail(portfolio.getThumbnail())
                .build();
    }
}
