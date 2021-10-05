package com.example.portfolist.domain.auth.service;

import com.example.portfolist.ServiceTest;
import com.example.portfolist.domain.auth.dto.request.GithubUserLoginRequest;
import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.dto.response.GithubUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.AuthCheckFacade;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.auth.util.api.client.GithubClient;
import com.example.portfolist.domain.auth.util.api.dto.GithubResponse;
import com.example.portfolist.global.event.GlobalEventPublisher;
import com.example.portfolist.global.mail.HtmlSourceProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class AuthServiceTest extends ServiceTest {

    @Mock
    private AuthCheckFacade authCheckFacade;
    @Mock
    private AuthFacade authFacade;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HtmlSourceProvider htmlSourceProvider;
    @Mock
    private GlobalEventPublisher globalEventPublisher;

    @Mock
    private GithubClient githubClient;

    @InjectMocks
    private AuthService authService;

    @Nested
    @DisplayName("Normal User Login")
    class NormalUserLogin {

        private NormalUserLoginRequest makeRequest(String email, String password) throws NoSuchFieldException, IllegalAccessException {
            NormalUserLoginRequest request = new NormalUserLoginRequest();
            inputField(request, "email", email);
            inputField(request, "password", password);
            return request;
        }

        @Test
        @DisplayName("Success")
        public void normalUserLogin_Success() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUserLoginRequest request = makeRequest("testtest@gmail.com", "testPassword");

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password("testPassword")
                    .build();

            User user = User.builder()
                    .pk(1L)
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();

            given(authCheckFacade.findByNormalUserEmail("testtest@gmail.com")).willReturn(user);
            given(passwordEncoder.matches("testPassword", "testPassword"))
                    .willReturn(true);
            given(jwtTokenProvider.generateAccessToken(1L)).willReturn("accessToken");
            given(jwtTokenProvider.generateRefreshToken(1L)).willReturn("refreshToken");

            // when
            NormalUserLoginResponse response = authService.login(request);

            // then
            Assertions.assertEquals(response.getAccessToken(), "accessToken");
            Assertions.assertEquals(response.getRefreshToken(), "refreshToken");
        }

        @Test
        @DisplayName("PasswordNotMatchedException")
        public void normalUserLogin_PasswordNotMatchedException() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUserLoginRequest request = makeRequest("testtest@gmail.com", "testWrongPassword");

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password("testPassword")
                    .build();

            User user = User.builder()
                    .pk(1L)
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();

            given(authCheckFacade.findByNormalUserEmail("testtest@gmail.com")).willReturn(user);
            given(passwordEncoder.matches("testWrongPassword","testPassword"))
                    .willReturn(false);

            // when -> then
            Assertions.assertThrows(PasswordNotMatchedException.class, () -> authService.login(request));

        }

    }

    @Nested
    @DisplayName("Github User Login")
    class GithubUserLogin {

        private GithubUserLoginRequest makeRequest(String githubToken) throws NoSuchFieldException, IllegalAccessException {
            GithubUserLoginRequest request = new GithubUserLoginRequest();
            inputField(request, "githubToken", githubToken);
            return request;
        }

        @DisplayName("First Login")
        @ParameterizedTest(name = "{argumentsWithNames}")
        @ValueSource(strings = { "name" })
        @NullSource
        public void githubUserFirstLogin(String name) throws NoSuchFieldException, IllegalAccessException {
            // given
            GithubUserLoginRequest request = makeRequest("githubToken");

            GithubResponse githubResponse = new GithubResponse();
            inputField(githubResponse, "login", "nickname");
            inputField(githubResponse, "avatarUrl", "profile");
            inputField(githubResponse, "name", name);
            given(githubClient.getUserInfo("token githubToken")).willReturn(githubResponse);

            given(authFacade.findUserByGithubId("nickname")).willReturn(Optional.empty());
            User user = User.builder()
                    .pk(1L)
                    .githubId("nickname")
                    .name(name==null ? "nickname" : name)
                    .url("profile")
                    .build();
            given(authFacade.save(any(User.class))).willReturn(user);

            given(jwtTokenProvider.generateAccessToken(1L)).willReturn("accessToken");
            given(jwtTokenProvider.generateRefreshToken(1L)).willReturn("refreshToken");

            // when
            GithubUserLoginResponse response = authService.login(request);

            // then
            Assertions.assertEquals(response.getAccessToken(), "accessToken");
            Assertions.assertEquals(response.getRefreshToken(), "refreshToken");
        }

        @DisplayName("Login")
        @ParameterizedTest(name = "{argumentsWithNames}")
        @ValueSource(strings = { "name" })
        @NullSource
        public void githubUserLogin(String name) throws NoSuchFieldException, IllegalAccessException {
            // given
            GithubUserLoginRequest request = makeRequest("githubToken");

            GithubResponse githubResponse = new GithubResponse();
            inputField(githubResponse, "login", "nickname");
            inputField(githubResponse, "avatarUrl", "profile");
            inputField(githubResponse, "name", name);
            given(githubClient.getUserInfo("token githubToken")).willReturn(githubResponse);

            User user = User.builder()
                    .pk(1L)
                    .githubId("nickname")
                    .name(name==null ? "nickname" : name)
                    .url("profile")
                    .build();
            given(authFacade.findUserByGithubId("nickname")).willReturn(Optional.of(user));

            given(jwtTokenProvider.generateAccessToken(1L)).willReturn("accessToken");
            given(jwtTokenProvider.generateRefreshToken(1L)).willReturn("refreshToken");

            // when
            GithubUserLoginResponse response = authService.login(request);

            // then
            Assertions.assertEquals(response.getAccessToken(), "accessToken");
            Assertions.assertEquals(response.getRefreshToken(), "refreshToken");
        }

    }

}