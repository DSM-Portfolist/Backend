package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioListResponse;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.RecentPortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.ThisMonthPortfolioResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PortfolioService {
    PortfolioListResponse getPortfolioList(Pageable pageable, List<String> fieldList);
    PortfolioResponse getPortfolioInfo(long portfolioId);
    void createPortfolio(PortfolioRequest request, MultipartFile file, List<List<MultipartFile>> boxImgListList);
    void deletePortfolio(long portfolioId);
    void updatePortfolio(long portfolioId);
    RecentPortfolioResponse getRecentPortfolio(int size);
    ThisMonthPortfolioResponse getThisMonthPortfolio();
    PortfolioListResponse getPortfolioByUser(long userId);
}
