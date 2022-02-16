package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioPreview;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuerydslRepository {

    Page<PortfolioPreview> getPortfolioList(Pageable pageable, List<String> fieldCond, String query, String searchType);
    List<String> getFieldKindContentByportfolioId(long id);
    Portfolio findThisMonthPortfolio();
    List<PortfolioPreview> findAllByUser(User byUser);
    List<PortfolioPreview> findMyTouchingPortfolio(Pageable pageable, User byUser);
}
