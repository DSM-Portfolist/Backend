package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.ServiceTest;
import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.AuthCheckFacade;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.domain.mypage.dto.request.UserInfoChangeRequest;
import com.example.portfolist.domain.mypage.dto.response.UserInfoGetResponse;
import com.example.portfolist.domain.mypage.repository.MypageFacade;
import com.example.portfolist.domain.portfolio.repository.PortfolioFacade;
import com.example.portfolist.global.file.FileUploadProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    class RegisterProfile {

        @ParameterizedTest(name = "Success {argumentsWithNames}")
        @ValueSource(strings = { "url" })
        @NullSource
        void registerProfile_Success(String url) {
            // given
            User user = User.builder()
                    .name("가나다")
                    .url(url)
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
            if(url == null)
                verify(fileUploadProvider, never()).deleteFile(any());
            else
                verify(fileUploadProvider, times(1)).deleteFile("url");
        }

    }

    @Nested
    @DisplayName("Delete Profile")
    class DeleteProfile {

        @ParameterizedTest(name = "Success {argumentsWithNames}")
        @ValueSource(strings = { "url" })
        @NullSource
        void deleteProfile_Success(String url) {
            // given
            User user = User.builder()
                    .name("가나다")
                    .url(url)
                    .build();

            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .user(user)
                    .build();

            // when
            mypageService.deleteProfile(normalUser);

            // then
            if(url == null)
                verify(fileUploadProvider, never()).deleteFile(any());
            else
                verify(fileUploadProvider, times(1)).deleteFile("url");
        }

    }

    @Nested
    @DisplayName("Get User Info")
    class GetUserInfo {

        @Test
        @DisplayName("Success")
        void getUserInfo_Success() {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();

            // when
            UserInfoGetResponse response = mypageService.getUserInfo(user);

            // then
            Assertions.assertEquals(response.getName(), "가나다");
            Assertions.assertNull(response.getField());
            Assertions.assertNull(response.getIntroduce());

        }

    }

    @Nested
    @DisplayName("Change User Info")
    class ChangeUserInfo {

        private UserInfoChangeRequest makeRequest(List<Integer> field, String introduce, String name) throws NoSuchFieldException, IllegalAccessException {
            UserInfoChangeRequest request = new UserInfoChangeRequest();
            inputField(request, "field", field);
            inputField(request, "introduce", introduce);
            inputField(request, "name", name);
            return request;
        }

        @Test
        @DisplayName("Success")
        void changeUserInfoSuccess() throws NoSuchFieldException, IllegalAccessException {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();

            FieldKind fieldKind = FieldKind.builder()
                    .pk(1)
                    .content("백엔드")
                    .build();

            List<Field> fieldList = new ArrayList<>();
            fieldList.add(
                    new com.example.portfolist.domain.auth.entity.Field(1L, user, fieldKind)
            );

            List<Integer> field = new ArrayList<>();
            field.add(2);
            UserInfoChangeRequest request = makeRequest(field, "바람이 불지 않으면 노를 저어라", "김땡땡");

            given(authFacade.findFieldByUser(any())).willReturn(fieldList);

            // when
            mypageService.changeUserInfo(request, user);

            // then
            verify(authFacade, times(1)).delete(any(List.class));
            verify(authFacade, times(1)).save(any(List.class));
        }

    }

}
