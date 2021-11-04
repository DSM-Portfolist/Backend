package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.ContainerRequest;
import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PortfolioService {
    PortfolioListResponse getPortfolioList(Pageable pageable, List<String> fieldList);
    PortfolioResponse getPortfolioInfo(long portfolioId);
    Long createPortfolio(PortfolioRequest request, MultipartFile file);
    void createContainer(ContainerRequest containerRequest, List<MultipartFile> containerImgList);
    void deletePortfolio(long portfolioId);
    Long updatePortfolio(long portfolioId, PortfolioRequest request, MultipartFile file);
    List<RecentPortfolioResponse> getRecentPortfolio(Pageable pageable);
    ThisMonthPortfolioResponse getThisMonthPortfolio();
    List<PortfolioPreview> getPortfolioByUser(long userId);
    List<PortfolioPreview> getMyTouchingPortfolio(Pageable pageable, long userId);
    void updateContainer(long portfolioId, ContainerRequest request, MultipartFile file);
}
