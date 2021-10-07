package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.ServiceTest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.AuthCheckFacade;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.domain.mypage.repository.MypageFacade;
import com.example.portfolist.domain.portfolio.repository.PortfolioFacade;
import com.example.portfolist.global.file.FileUploadProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MypageServiceTest extends ServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FileUploadProvider fileUploadProvider;

    @Mock
    private AuthFacade authFacade;
    @Mock
    private AuthCheckFacade authCheckFacade;
    @Mock
    private MypageFacade mypageFacade;
    @Mock
    private PortfolioFacade portfolioFacade;

    @InjectMocks
    private MypageService mypageService;

    @Nested
    @DisplayName("Change Password")
    class ChangePassword {

        private PasswordChangeRequest makeRequest(String nowPassword, String newPassword) throws NoSuchFieldException, IllegalAccessException {
            PasswordChangeRequest request = new PasswordChangeRequest();
            inputField(request, "nowPassword", nowPassword);
            inputField(request, "newPassword", newPassword);
            return request;
        }

        @Test
        @DisplayName("Success")
        void changePassword_Success() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password("testPassword")
                    .build();

            PasswordChangeRequest request = makeRequest("testPassword", "newPassword");

            given(passwordEncoder.matches("testPassword", "testPassword")).willReturn(true);

            // when
            mypageService.changePassword(request, normalUser);

            // then
            verify(passwordEncoder, times(1)).encode(any());
        }

        @Test
        @DisplayName("PasswordNotMatchedException")
        void changePassword_PasswordNotMatchedException() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password("testPassword")
                    .build();

            PasswordChangeRequest request = makeRequest("testFakePassword", "newPassword");

            given(passwordEncoder.matches("testFakePassword", "testPassword")).willReturn(false);

            // when -> then
            Assertions.assertThrows(PasswordNotMatchedException.class, () -> mypageService.changePassword(request, normalUser));
        }

    }

    @Nested
    @DisplayName("Check Password")
    class CheckPassword {

        private PasswordCheckRequest makeRequest(String nowPassword) throws NoSuchFieldException, IllegalAccessException {
            PasswordCheckRequest request = new PasswordCheckRequest();
            inputField(request, "nowPassword", nowPassword);
            return request;
        }

        @Test
        @DisplayName("Success")
        void changePassword_Success() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password("testPassword")
                    .build();

            PasswordCheckRequest request = makeRequest("testPassword");

            given(passwordEncoder.matches("testPassword", "testPassword")).willReturn(true);

            // when -> then
            mypageService.checkPassword(request, normalUser);
        }

        @Test
        @DisplayName("PasswordNotMatchedException")
        void changePassword_PasswordNotMatchedException() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password("testPassword")
                    .build();

            PasswordCheckRequest request = makeRequest("testFakePassword");

            given(passwordEncoder.matches("testFakePassword", "testPassword")).willReturn(false);

            // when -> then
            Assertions.assertThrows(PasswordNotMatchedException.class, () -> mypageService.checkPassword(request, normalUser));
        }

    }

}
