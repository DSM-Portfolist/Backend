package com.example.portfolist.domain.portfolio.dto.portfolio.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioListResponse {

    private long totalElements;

    private List<PortfolioPreview> portfolioList;
}
