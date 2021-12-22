package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.constant.UserConstant;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TouchingControllerTest extends ApiTest {

    NormalUser normalUser;
    User user;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        normalUser = NormalUser.builder()
                .email(UserConstant.EMAIL)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build();
        normalUserRepository.save(normalUser);
        user = User.builder()
                .name(UserConstant.NAME)
                .introduce(UserConstant.INTRODUCE)
                .normalUser(normalUser)
                .build();
        userRepository.save(user);
    }

    @Test
    public void 터칭_201() throws Exception {
        auth();

        Portfolio portfolio = createPortfolio(user, "title", "introduce");
        long pk = portfolio.getPk();

        requestMvc(post("/touching/" + pk))
                .andExpect(status().isCreated());

        assertEquals(touchingRepository.findByPortfolio(portfolio).size(), 1);
    }

    @Test
    public void 터칭_취소_204() throws Exception {
        auth();

        Portfolio portfolio = createPortfolio(user, "title", "introduce");
        long pk = portfolio.getPk();

        requestMvc(post("/touching/" + pk))
                .andExpect(status().isCreated());

        assertEquals(touchingRepository.findByPortfolio(portfolio).size(), 1);

        requestMvc(delete("/touching/" + pk))
            .andExpect(status().isNoContent());

        assertEquals(touchingRepository.findByPortfolio(portfolio).size(), 0);
    }

    private void auth() {
        setToken(jwtTokenProvider.generateAccessToken(user.getPk()));
    }
}