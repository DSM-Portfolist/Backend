package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.Portfolio;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class PortfolioResponse {



    public static PortfolioResponse of(Portfolio portfolio) {
        return PortfolioResponse.builder().build();
    }
}
