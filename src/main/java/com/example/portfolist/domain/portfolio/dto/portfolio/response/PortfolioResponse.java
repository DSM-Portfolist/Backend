package com.example.portfolist.domain.portfolio.dto.portfolio.response;

import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class PortfolioResponse {



    public static PortfolioResponse of(Portfolio portfolio) {
        return PortfolioResponse.builder().build();
    }
}
