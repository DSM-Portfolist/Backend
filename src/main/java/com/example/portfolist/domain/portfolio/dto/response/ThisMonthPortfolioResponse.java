package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ThisMonthPortfolioResponse {

    private long id;

    private String img;

    private String name;

    private String introduce;

    public static ThisMonthPortfolioResponse of(Portfolio thisMonthPortfolio) {
        return ThisMonthPortfolioResponse.builder()
                .id(thisMonthPortfolio.getPk())
                .img(thisMonthPortfolio.getUrl())
                .name(thisMonthPortfolio.getUser().getName())
                .introduce(thisMonthPortfolio.getIntroduce())
                .build();
    }
}
