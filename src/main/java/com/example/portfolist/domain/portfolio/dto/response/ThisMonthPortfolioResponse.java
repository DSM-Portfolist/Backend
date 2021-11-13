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

    private String thumbnail;

    private String title;

    private String name;

    private String introduce;

    private long totalTouching;

    public static ThisMonthPortfolioResponse of(Portfolio thisMonthPortfolio) {
        return ThisMonthPortfolioResponse.builder()
                .id(thisMonthPortfolio.getPk())
                .title(thisMonthPortfolio.getTitle())
                .thumbnail(thisMonthPortfolio.getThumbnail())
                .name(thisMonthPortfolio.getUser().getName())
                .introduce(thisMonthPortfolio.getIntroduce())
                .totalTouching(thisMonthPortfolio.getTouchingList().size())
                .build();
    }
}
