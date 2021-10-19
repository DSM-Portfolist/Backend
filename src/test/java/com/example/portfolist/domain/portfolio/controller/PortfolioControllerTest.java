package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioPreview;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.PortfolioField;
import com.example.portfolist.domain.portfolio.repository.portfolio.QuerydslRepositoryImpl;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioControllerTest extends ApiTest {

    @Autowired
    QuerydslRepositoryImpl querydslRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void before() {

        User user1 = registerUser("곽도현", "곽도현입니다");
        User user2 = registerUser("곽도현", "곽도현입니다");

        Portfolio portfolio1 = createPortfolio(user1, "포트폴리오 제목1", "소개1");
        Portfolio portfolio2 = createPortfolio(user1, "포트폴리오 제목1", "소개1");

        FieldKind field1 = createFieldKind("웹");

        PortfolioField portfolioField1 = createPortfolioField(portfolio1, field1);

        createTouching(user2, portfolio1);

        createTouching(user1, portfolio1);

        createComment(user1, portfolio1, "댓글");
    }

    @Test
    public void 포트폴리오_리스트_조회() {
        //when
        Pageable pageable = PageRequest.of(0, 20);

        //then
        Page<PortfolioPreview> portfolioList = querydslRepository.getPortfolioList(pageable, new ArrayList<>());

        assertThat(portfolioList.getTotalElements()).isEqualTo(2);

        for (PortfolioPreview portfolioPreview : portfolioList) {
            System.out.println("portfolioPreview = " + portfolioPreview);
        }
    }
}