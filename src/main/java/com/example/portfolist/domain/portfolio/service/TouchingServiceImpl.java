package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.TouchingRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TouchingServiceImpl implements TouchingService{

    private final TouchingRepository touchingRepository;
    private final PortfolioRepository portfolioRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void createTouching(long portfolioId) {
        touchingRepository.save(Touching.toEntity(getCurrentUser(), getPortfolio(portfolioId)));
    }

    @Override
    public void deleteTouching(long portfolioId) {
        touchingRepository.deleteById(new TouchingId(getCurrentUser().getPk(), getPortfolio(portfolioId).getPk()));
    }

    private User getCurrentUser() {
        return authenticationFacade.getUser();
    }

    private Portfolio getPortfolio(long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(PortfolioNotFoundException::new);
    }
}
