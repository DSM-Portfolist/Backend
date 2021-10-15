package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService{

    private final PortfolioRepository portfolioRepository;

    @Override
    public PortfolioListResponse getPortfolioList(Pageable page, List<String> fieldList) {

        Page<PortfolioPreview> portfolioList = portfolioRepository.getPortfolioList(page, fieldList);

        return new PortfolioListResponse(
                portfolioList.getTotalElements(),
                portfolioList.getContent()
        );
    }

    @Override
    public PortfolioResponse getPortfolio(long projectId) {
        return null;
    }

    @Override
    public void createPortfolio(PortfolioRequest request) {

    }

    @Override
    public void deletePortfolio(long projectId) {

    }

    @Override
    public void updatePortfolio(long projectId) {

    }

    @Override
    public RecentPortfolioResponse getRecentPortfolio(int size) {
        return null;
    }

    @Override
    public ThisMonthPortfolioResponse getThisMonthPortfolio() {
        return null;
    }

    @Override
    public PortfolioListResponse getPortfolioByUser(long userId) {
        return null;
    }
}
