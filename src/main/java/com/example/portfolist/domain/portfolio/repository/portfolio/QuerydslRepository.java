package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.portfolio.dto.portfolio.response.PortfolioPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuerydslRepository {

    Page<PortfolioPreview> getPortfolioList(Pageable pageable, List<String> fieldCond);
    List<String> getFieldKindContentByPortfolioId(long id);
}
