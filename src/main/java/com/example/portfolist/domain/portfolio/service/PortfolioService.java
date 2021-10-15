package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioListResponse;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.RecentPortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.ThisMonthPortfolioResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PortfolioService {
    PortfolioListResponse getPortfolioList(Pageable pageable, List<String> fieldList);
    PortfolioResponse getPortfolio(long projectId);
    void createPortfolio(PortfolioRequest request);
    void deletePortfolio(long projectId);
    void updatePortfolio(long projectId);
    RecentPortfolioResponse getRecentPortfolio(int size);
    ThisMonthPortfolioResponse getThisMonthPortfolio();
    PortfolioListResponse getPortfolioByUser(long userId);
}
