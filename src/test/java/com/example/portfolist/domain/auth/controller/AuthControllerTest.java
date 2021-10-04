package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.util.api.client.GithubClient;
import com.example.portfolist.global.event.GlobalEventPublisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends ApiTest {

    @MockBean
    private GlobalEventPublisher globalEventPublisher;
    @MockBean
    private GithubClient githubClient;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NormalUserRepository normalUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clear() {
        setToken(null);

    }

    @Test
    @DisplayName("일반 유저 로그인 201")
    void normalUserLogin_201() throws Exception {
        // given
        NormalUserLoginRequest request = new NormalUserLoginRequest();
        inputField(request, "email", "testtest@gmail.com");
        inputField(request, "password", "testPassword");

        NormalUser normalUser = NormalUser.builder()
                .email("testtest@gmail.com")
                .password(passwordEncoder.encode("testPassword"))
                .build();
        normalUser = normalUserRepository.save(normalUser);

        User user = User.builder()
                .normalUser(normalUser)
                .name("가나다")
                .build();
        userRepository.save(user);

        // when
        ResultActions resultActions = requestMvc(post("/login/normal"), request);

        // then
        resultActions.andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    @DisplayName("일반 유저 로그인 400")
    void normalUserLogin_400() throws Exception {
        // given
        NormalUserLoginRequest request = new NormalUserLoginRequest();

        // when
        ResultActions resultActions = requestMvc(post("/login/normal"), request);

        // then
        resultActions.andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    @DisplayName("일반 유저 로그인 401")
    void normalUserLogin_401() throws Exception {
        // given
        NormalUserLoginRequest request = new NormalUserLoginRequest();
        inputField(request, "email", "testtest@gmail.com");
        inputField(request, "password", "testPassword");

        NormalUser normalUser = NormalUser.builder()
                .email("testtest@gmail.com")
                .password(passwordEncoder.encode("testFakePassword"))
                .build();
        normalUser = normalUserRepository.save(normalUser);

        User user = User.builder()
                .normalUser(normalUser)
                .name("가나다")
                .build();
        userRepository.save(user);

        // when
        ResultActions resultActions = requestMvc(post("/login/normal"), request);

        // then
        resultActions.andExpect(status().is(401))
                .andDo(print());
    }

    @Test
    @DisplayName("일반 유저 로그인 404")
    void normalUserLogin_404() throws Exception {
        // given
        NormalUserLoginRequest request = new NormalUserLoginRequest();
        inputField(request, "email", "testtest@gmail.com");
        inputField(request, "password", "testPassword");

        // when
        ResultActions resultActions = requestMvc(post("/login/normal"), request);

        // then
        resultActions.andExpect(status().is(404))
                .andDo(print());
    }

}
