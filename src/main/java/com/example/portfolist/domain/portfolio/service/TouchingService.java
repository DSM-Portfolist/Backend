package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.NoticeType;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.TouchingRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.global.event.GlobalEventPublisher;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TouchingService {

    private final TouchingRepository touchingRepository;
    private final PortfolioRepository portfolioRepository;

    private final AuthenticationFacade authenticationFacade;

    private final GlobalEventPublisher eventPublisher;

    public void createTouching(long portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        User user = getCurrentUser();
        touchingRepository.save(Touching.toEntity(user, portfolio));
        eventPublisher.makeNotice(user, portfolio.getUser(), NoticeType.TOUCHING, portfolio);
    }

    public void deleteTouching(long portfolioId) {
        touchingRepository.deleteById(new TouchingId(getCurrentUser().getId(), getPortfolio(portfolioId).getId()));
    }

    private User getCurrentUser() {
        return authenticationFacade.getUser();
    }

    private Portfolio getPortfolio(long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(PortfolioNotFoundException::new);
    }
}
