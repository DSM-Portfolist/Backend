package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.ServiceTest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @Nested
    @DisplayName("Register Profile")
    class registerProfile {

        @Test
        @DisplayName("None Profile Success")
        void registerProfile_noneProfile_Success() {
            // given
            User user = User.builder()
                    .name("가나다")
                    .build();

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .user(user)
                    .build();

            MockMultipartFile file =
                    new MockMultipartFile("file", "hello.png", "png", "hello".getBytes(StandardCharsets.UTF_8));

            given(fileUploadProvider.uploadFile(any(MultipartFile.class))).willReturn("url");

            // when
            mypageService.registerProfile(file, normalUser);

            // then
            verify(fileUploadProvider, never()).deleteFile(any());
        }

        @Test
        @DisplayName("Exists Profile Success")
        void registerProfile_existsProfile_Success() {
            // given
            User user = User.builder()
                    .name("가나다")
                    .url("url")
                    .build();

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .user(user)
                    .build();

            MockMultipartFile file =
                    new MockMultipartFile("file", "hello.png", "png", "hello".getBytes(StandardCharsets.UTF_8));

            given(fileUploadProvider.uploadFile(any(MultipartFile.class))).willReturn("url");

            // when
            mypageService.registerProfile(file, normalUser);

            // then
            verify(fileUploadProvider, times(1)).deleteFile(any());
        }

    }

    @Nested
    @DisplayName("Delete Profile")
    class deleteProfile {

        @Test
        @DisplayName("None Profile Success")
        void deleteProfile_noneProfile_Success() {
            // given
            User user = User.builder()
                    .name("가나다")
                    .build();

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .user(user)
                    .build();

            // when
            mypageService.deleteProfile(normalUser);

            // then
            verify(fileUploadProvider, never()).deleteFile(any());
        }

        @Test
        @DisplayName("Exists Profile Success")
        void deleteProfile_existsProfile_Success() {
            // given
            User user = User.builder()
                    .name("가나다")
                    .url("url")
                    .build();

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .user(user)
                    .build();

            // when
            mypageService.deleteProfile(normalUser);

            // then
            verify(fileUploadProvider, times(1)).deleteFile("url");
        }

    }

}
