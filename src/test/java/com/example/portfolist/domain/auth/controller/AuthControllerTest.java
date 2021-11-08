package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.dto.request.*;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.entity.redis.Certification;
import com.example.portfolist.domain.auth.exception.OauthServerException;
import com.example.portfolist.domain.auth.util.api.client.GithubCodeClient;
import com.example.portfolist.domain.auth.util.api.client.GithubTokenClient;
import com.example.portfolist.domain.auth.util.api.dto.GithubCodeResponse;
import com.example.portfolist.domain.auth.util.api.dto.GithubTokenResponse;
import com.example.portfolist.global.event.GlobalEventPublisher;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends ApiTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private GlobalEventPublisher globalEventPublisher;
    @MockBean
    private GithubCodeClient githubCodeClient;
    @MockBean
    private GithubTokenClient githubTokenClient;

    @Nested
    @DisplayName("일반 유저 로그인")
    class NormalUserLogin{

        private NormalUserLoginRequest makeRequest(String email, String password) throws NoSuchFieldException, IllegalAccessException {
            NormalUserLoginRequest request = new NormalUserLoginRequest();
            inputField(request, "email", email);
            inputField(request, "password", password);
            return request;
        }

        @Test
        @DisplayName("201")
        void normalUserLogin_201() throws Exception {
            // given
            NormalUserLoginRequest request = makeRequest("testtest@gmail.com", "testPassword");

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
            resultActions.andExpect(status().is(201));
        }

        @Test
        @DisplayName("400")
        void normalUserLogin_400() throws Exception {
            // given
            NormalUserLoginRequest request = makeRequest(null, null);

            // when
            ResultActions resultActions = requestMvc(post("/login/normal"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("401")
        void normalUserLogin_401() throws Exception {
            // given
            NormalUserLoginRequest request = makeRequest("testtest@gmail.com", "testPassword");

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
            resultActions.andExpect(status().is(401));
        }

        @Test
        @DisplayName("404")
        void normalUserLogin_404() throws Exception {
            // given
            NormalUserLoginRequest request = makeRequest("testtest@gmail.com", "testPassword");

            // when
            ResultActions resultActions = requestMvc(post("/login/normal"), request);

            // then
            resultActions.andExpect(status().is(404));
        }

    }

    @Nested
    @DisplayName("깃허브 유저 로그인")
    class GithubUserLogin{

        private GithubUserLoginRequest makeRequest(String code) throws NoSuchFieldException, IllegalAccessException {
            GithubUserLoginRequest request = new GithubUserLoginRequest();
            inputField(request, "code", code);
            return request;
        }

        @Test
        @DisplayName("201")
        void githubUserLogin_first_201() throws Exception {
            // given
            GithubCodeResponse githubCodeResponse = new GithubCodeResponse();
            inputField(githubCodeResponse, "accessToken", "githubToken");

            GithubTokenResponse githubTokenResponse = new GithubTokenResponse();
            inputField(githubTokenResponse, "login", "nickname");
            inputField(githubTokenResponse, "avatarUrl", "profile");
            inputField(githubTokenResponse, "name", "name");

            given(githubCodeClient.getUserToken("asdf", "asdf", "code")).willReturn(githubCodeResponse);
            given(githubTokenClient.getUserInfo("token githubToken")).willReturn(githubTokenResponse);

            GithubUserLoginRequest request = makeRequest("code");

            // when
            ResultActions resultActions = requestMvc(post("/login/github"), request);

            // then
            resultActions.andExpect(status().is(201));
        }

        @Test
        @DisplayName("201")
        void githubUserLogin_201() throws Exception {
            // given
            GithubCodeResponse githubCodeResponse = new GithubCodeResponse();
            inputField(githubCodeResponse, "accessToken", "githubToken");

            GithubTokenResponse githubTokenResponse = new GithubTokenResponse();
            inputField(githubTokenResponse, "login", "nickname");
            inputField(githubTokenResponse, "avatarUrl", "profile");
            inputField(githubTokenResponse, "name", "name");

            given(githubCodeClient.getUserToken("asdf", "asdf", "code")).willReturn(githubCodeResponse);
            given(githubTokenClient.getUserInfo("token githubToken")).willReturn(githubTokenResponse);

            GithubUserLoginRequest request = makeRequest("code");

            User user = User.builder()
                    .githubId("nickname")
                    .name("name")
                    .build();
            userRepository.save(user);

            // when
            ResultActions resultActions = requestMvc(post("/login/github"), request);

            // then
            resultActions.andExpect(status().is(201));
        }

        @Test
        @DisplayName("400")
        void githubUserLogin_400() throws Exception {
            // given
            GithubUserLoginRequest request = makeRequest(null);

            // when
            ResultActions resultActions = requestMvc(post("/login/github"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("401")
        void githubUserLogin_401() throws Exception {
            // given
            GithubCodeResponse githubCodeResponse = new GithubCodeResponse();
            inputField(githubCodeResponse, "accessToken", "githubToken");

            given(githubCodeClient.getUserToken("asdf", "asdf", "code")).willReturn(githubCodeResponse);
            given(githubTokenClient.getUserInfo("token githubToken")).willThrow(new OauthServerException(401, "Invalid Token"));

            GithubUserLoginRequest request = makeRequest("code");

            // when
            ResultActions resultActions = requestMvc(post("/login/github"), request);

            // then
            resultActions.andExpect(status().is(401));
        }

    }

    @Nested
    @DisplayName("이메일 인증")
    class EmailCertificate{

        private EmailCertificationRequest makeRequest(String email) throws NoSuchFieldException, IllegalAccessException {
            EmailCertificationRequest request = new EmailCertificationRequest();
            inputField(request, "email", email);
            return request;
        }

        @Test
        @DisplayName("200")
        void emailCertificate_200() throws Exception {
            // given
            EmailCertificationRequest request = makeRequest("testtest@gmail.com");

            // when
            ResultActions resultActions = requestMvc(post("/email"), request);

            // then
            resultActions.andExpect(status().is(200));
        }

        @Test
        @DisplayName("400")
        void emailCertificate_400() throws Exception {
            // given
            EmailCertificationRequest request = makeRequest(null);

            // when
            ResultActions resultActions = requestMvc(post("/email"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("409")
        void emailCertificate_409() throws Exception {
            // given
            EmailCertificationRequest request = makeRequest("testtest@gmail.com");

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
            ResultActions resultActions = requestMvc(post("/email"), request);

            // then
            resultActions.andExpect(status().is(409));
        }

    }

    @Nested
    @DisplayName("일반 유저 회원가입")
    class NormalUserJoin{

        private NormalUserJoinRequest makeRequest(String name, String email, String password, List<Integer> field) throws NoSuchFieldException, IllegalAccessException {
            NormalUserJoinRequest request = new NormalUserJoinRequest();
            inputField(request, "name", name);
            inputField(request, "email", email);
            inputField(request, "password", password);
            inputField(request, "field", field);
            return request;
        }

        @Test
        @DisplayName("201")
        void normalUserJoin_201() throws Exception {
            // given
            Certification certification = Certification.builder()
                    .id(1L)
                    .email("testtest@gmail.com")
                    .token("token")
                    .certification(true)
                    .exp(200000L)
                    .build();
            given(certificationRepository.findByEmail("testtest@gmail.com"))
                    .willReturn(Optional.of(certification));

            FieldKind fieldKind = FieldKind.builder()
                    .content("백엔드")
                    .build();
            fieldKind = fieldKindRepository.save(fieldKind);

            List<Integer> field = new ArrayList<>();
            field.add(fieldKind.getPk());
            NormalUserJoinRequest request = makeRequest("가나다", "testtest@gmail.com", "testPassword", field);

            // when
            ResultActions resultActions = requestMvc(post("/join"), request);

            // then
            resultActions.andExpect(status().is(201));
        }

        @Test
        @DisplayName("400")
        void normalUserJoin_400() throws Exception {
            // given
            NormalUserJoinRequest request = makeRequest(null, null, null, null);

            // when
            ResultActions resultActions = requestMvc(post("/join"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("401")
        void normalUserJoin_401() throws Exception {
            // given
            given(certificationRepository.findByEmail("testtest@gmail.com"))
                    .willReturn(Optional.empty());

            List<Integer> field = new ArrayList<>();
            field.add(1);
            NormalUserJoinRequest request = makeRequest("가나다", "testtest@gmail.com", "testPassword", field);

            // when
            ResultActions resultActions = requestMvc(post("/join"), request);

            // then
            resultActions.andExpect(status().is(401));
        }

        @Test
        @DisplayName("404")
        void normalUserJoin_404() throws Exception {
            // given
            Certification certification = Certification.builder()
                    .id(1L)
                    .email("testtest@gmail.com")
                    .token("token")
                    .certification(true)
                    .exp(200000L)
                    .build();
            given(certificationRepository.findByEmail("testtest@gmail.com"))
                    .willReturn(Optional.of(certification));

            List<Integer> field = new ArrayList<>();
            field.add(1);
            NormalUserJoinRequest request = makeRequest("가나다", "testtest@gmail.com", "testPassword", field);

            // when
            ResultActions resultActions = requestMvc(post("/join"), request);

            // then
            resultActions.andExpect(status().is(404));
        }

    }

    @Nested
    @DisplayName("토큰 리프레쉬")
    class TokenRefresh{

        private TokenRefreshRequest makeRequest(String token) throws NoSuchFieldException, IllegalAccessException {
            TokenRefreshRequest request = new TokenRefreshRequest();
            inputField(request, "refreshToken", token);
            return request;
        }

        @Test
        @DisplayName("201")
        void tokenRefresh_201() throws Exception {
            // given
            String token = jwtTokenProvider.generateRefreshToken(1L);
            given(refreshTokenRepository.existsByRefreshToken(token)).willReturn(true);

            TokenRefreshRequest request = makeRequest(token);

            // when
            ResultActions resultActions = requestMvc(post("/token-refresh"), request);

            // then
            resultActions.andExpect(status().is(201));
        }

        @Test
        @DisplayName("400")
        void tokenRefresh_400() throws Exception {
            // given
            TokenRefreshRequest request = makeRequest(null);

            // when
            ResultActions resultActions = requestMvc(post("/token-refresh"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("401")
        void tokenRefresh_401() throws Exception {
            // given
            String token = "이건 토큰 아니지롱";
            given(refreshTokenRepository.existsByRefreshToken(token)).willReturn(true);

            TokenRefreshRequest request = makeRequest(token);

            // when
            ResultActions resultActions = requestMvc(post("/token-refresh"), request);

            // then
            resultActions.andExpect(status().is(401));
        }

    }

}