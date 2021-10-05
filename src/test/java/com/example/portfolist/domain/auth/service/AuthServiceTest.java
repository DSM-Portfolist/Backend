package com.example.portfolist.domain.auth.service;

import com.example.portfolist.ServiceTest;
import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.AuthCheckFacade;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.auth.util.api.client.GithubClient;
import com.example.portfolist.global.event.GlobalEventPublisher;
import com.example.portfolist.global.mail.HtmlSourceProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Test
    @DisplayName("Normal user login")
    public void normalUserLogin() throws NoSuchFieldException, IllegalAccessException {
        // given
        NormalUserLoginRequest request = new NormalUserLoginRequest();
        inputField(request, "email", "testtest@gmail.com");
        inputField(request, "password", "testPassword");

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
        given(passwordEncoder.matches("testPassword","testPassword"))
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
    @DisplayName("Normal user login - PasswordNotMatchedException")
    public void normalUserLogin_PasswordNotMatchedException() throws NoSuchFieldException, IllegalAccessException {
        // given
        NormalUserLoginRequest request = new NormalUserLoginRequest();
        inputField(request, "email", "testtest@gmail.com");
        inputField(request, "password", "testPassword");

        NormalUser normalUser = NormalUser.builder()
                .email("testtest@gmail.com")
                .password("testWrongPassword")
                .build();

        User user = User.builder()
                .pk(1L)
                .normalUser(normalUser)
                .name("가나다")
                .build();

        given(authCheckFacade.findByNormalUserEmail("testtest@gmail.com")).willReturn(user);
        given(passwordEncoder.matches("testPassword","testWrongPassword"))
                .willReturn(false);

        // when -> then
        Assertions.assertThrows(PasswordNotMatchedException.class, () -> authService.login(request));
    }

}
