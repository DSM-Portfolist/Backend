package com.example.portfolist.domain.portfolio.repository;

import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.repository.touching.TouchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortfolioFacade {

    private final TouchingRepository touchingRepository;

    public Page<Touching> findTouchingAll(int page, int size) {
        return touchingRepository.findAll(PageRequest.of(page, size));
    }

}
