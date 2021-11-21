package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioPreview;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioServiceTest extends ApiTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    FieldKind fieldKind1;
    FieldKind fieldKind2;
    FieldKind fieldKind3;
    FieldKind fieldKind4;

    User user1;
    User user2;
    User user3;
    User user4;

    @BeforeEach
    public void before() {
        fieldKind1 = createFieldKind("서버");
        fieldKind2 = createFieldKind("앱");
        fieldKind3 = createFieldKind("프론트");
        fieldKind4 = createFieldKind("빅데이터");

        user1 = createUser("홍길동", "곽도현입니다", null);
        user2 = createUser("김갑자", "김갑자입니다", null);
        user3 = createUser("홍갑순", "김갑자입니다", null);
        user4 = createUser("홍수여", "김갑자입니다", null);

        Portfolio portfolio1 = createPortfolio(user1, "포트폴리오 제목1", "소개1");

        createPortfolioField(portfolio1, fieldKind1);
        createPortfolioField(portfolio1, fieldKind2);
        createTouching(user1, portfolio1);
        createTouching(user2, portfolio1);
        createTouching(user3, portfolio1);
        createTouching(user4, portfolio1);
        createComment(user1, portfolio1, "댓글1");

        Portfolio portfolio2 = createPortfolio(user1, "포트폴리오 제목2", "소개2");

        createPortfolioField(portfolio2, fieldKind3);
        createPortfolioField(portfolio2, fieldKind4);
        createTouching(user1, portfolio2);
        createTouching(user2, portfolio2);
        createTouching(user3, portfolio2);

        createComment(user1, portfolio2, "댓글1");
        createComment(user3, portfolio2, "댓글2");
        createComment(user4, portfolio2, "댓글3");
    }

    @Test
    public void 포트폴리오_리스트_조회_필터링_없을_때() {
        //when
        Pageable pageable = PageRequest.of(0, 5);
        setToken(jwtTokenProvider.generateAccessToken(user1.getPk()));

        //given
        Page<PortfolioPreview> portfolioList = portfolioRepository
                .getPortfolioList(pageable, new ArrayList<>(), "포", "title");

        //then
        assertThat(portfolioList.getTotalElements()).isEqualTo(2);

        for (PortfolioPreview portfolioPreview : portfolioList) {
            System.out.println("title = " + portfolioPreview.getTitle());
            System.out.println("totalComment = " + portfolioPreview.getTotalComment());
            System.out.println("totalTouching = " + portfolioPreview.getTotalTouching());
            portfolioPreview.getField().forEach(p -> System.out.println("p = " + p));
        }
    }
}