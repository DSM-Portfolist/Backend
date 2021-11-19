package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PortfolioService {
    PortfolioListResponse getPortfolioList(Pageable pageable, List<String> fieldList);
    PortfolioResponse getPortfolioInfo(long portfolioId);
    void createPortfolio(PortfolioRequest request);
    void deletePortfolio(long portfolioId);
    Long updatePortfolio(long portfolioId, PortfolioRequest request);
    List<RecentPortfolioResponse> getRecentPortfolio(Pageable pageable);
    ThisMonthPortfolioResponse getThisMonthPortfolio();
    List<PortfolioPreview> getPortfolioByUser(long userId);
    List<PortfolioPreview> getMyTouchingPortfolio(Pageable pageable, long userId);
    PortfolioListResponse searchPortfolio(Pageable pageable, String query, String searchType);
}
