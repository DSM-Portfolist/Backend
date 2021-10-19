package com.example.portfolist.domain.portfolio.repository;

import com.example.portfolist.ApiTest;
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

class PortfolioRepositoryTest extends ApiTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void before() {
        createFieldKind("서버");
        createFieldKind("앱");
        createFieldKind("프론트");
        createFieldKind("빅데이터");
    }

    @Test
    public void 포트폴리오_리스트_조회_필터링_없을_때() {
        //when
        Pageable pageable = PageRequest.of(0, 5);

        User user1 = createUser("홍길동", "곽도현입니다", null);
        User user2 = createUser("김갑자", "김갑자입니다", null);
        User user3 = createUser("홍갑순", "김갑자입니다", null);
        User user4 = createUser("홍수여", "김갑자입니다", null);

        Portfolio portfolio1 = createPortfolio(user1, "포트폴리오 제목1", "소개1");

        createPortfolioField(portfolio1, fieldKindRepository.getById(1));
        createPortfolioField(portfolio1, fieldKindRepository.getById(2));
        createTouching(user1, portfolio1);
        createTouching(user2, portfolio1);
        createComment(user1, portfolio1, "댓글1");

        Portfolio portfolio2 = createPortfolio(user1, "포트폴리오 제목2", "소개2");

        createPortfolioField(portfolio2, fieldKindRepository.getById(3));
        createPortfolioField(portfolio2, fieldKindRepository.getById(4));
        createTouching(user1, portfolio2);
        createTouching(user2, portfolio2);
        createTouching(user3, portfolio2);
        createTouching(user4, portfolio2);
        createComment(user1, portfolio2, "댓글1");
        createComment(user3, portfolio2, "댓글2");
        createComment(user4, portfolio2, "댓글3");

        setToken(jwtTokenProvider.generateAccessToken(user1.getPk()));

        //given
        Page<PortfolioPreview> portfolioList = portfolioRepository.getPortfolioList(pageable, new ArrayList<>());

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