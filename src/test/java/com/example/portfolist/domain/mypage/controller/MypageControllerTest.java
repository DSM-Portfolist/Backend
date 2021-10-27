package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.domain.mypage.dto.request.UserInfoChangeRequest;
import com.example.portfolist.domain.mypage.entity.NoticeType;
import com.example.portfolist.domain.mypage.entity.Notification;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import com.example.portfolist.global.file.FileUploadProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MypageControllerTest extends ApiTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private FileUploadProvider fileUploadProvider;

    @Nested
    @DisplayName("비밀번호 변경")
    class ChangePassword {

        private PasswordChangeRequest makeRequest(String nowPassword, String newPassword) throws NoSuchFieldException, IllegalAccessException {
            PasswordChangeRequest request = new PasswordChangeRequest();
            inputField(request, "nowPassword", nowPassword);
            inputField(request, "newPassword", newPassword);
            return request;
        }

        @Test
        @DisplayName("200")
        void changePassword_200() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            PasswordChangeRequest request = makeRequest("testPassword", "newPassword");
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/password"), request);

            // then
            resultActions.andExpect(status().is(200));
        }

        @Test
        @DisplayName("400")
        void changePassword_400() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            PasswordChangeRequest request = makeRequest(null, null);
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/password"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("401")
        void changePassword_401() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testFakePassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            PasswordChangeRequest request = makeRequest("testPassword", "newPassword");
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/password"), request);

            // then
            resultActions.andExpect(status().is(401));
        }

    }

    @Nested
    @DisplayName("비밀번호 확인")
    class CheckPassword {

        private PasswordCheckRequest makeRequest(String nowPassword) throws NoSuchFieldException, IllegalAccessException {
            PasswordCheckRequest request = new PasswordCheckRequest();
            inputField(request, "nowPassword", nowPassword);
            return request;
        }

        @Test
        @DisplayName("200")
        void checkPassword_200() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            PasswordCheckRequest request = makeRequest("testPassword");
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(post("/user/password"), request);

            // then
            resultActions.andExpect(status().is(200));
        }

        @Test
        @DisplayName("400")
        void checkPassword_400() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            PasswordCheckRequest request = makeRequest(null);
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(post("/user/password"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("401")
        void checkPassword_401() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testFakePassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            PasswordCheckRequest request = makeRequest("testPassword");
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(post("/user/password"), request);

            // then
            resultActions.andExpect(status().is(401));
        }

    }

    @Nested
    @DisplayName("프로필 변경")
    class ChangeProfile {

        @Test
        @DisplayName("200")
        void changeProfile_200() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());
            MockMultipartFile file =
                    new MockMultipartFile("file", "hello.png", "image/png", "hello".getBytes(StandardCharsets.UTF_8));

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(multipart("/user/profile").file(file));

            // then
            resultActions.andExpect(status().is(200));
        }

        @Test
        @DisplayName("400")
        void changeProfile_400() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(post("/user/profile"));

            // then
            resultActions.andExpect(status().is(400));
        }
    }

    @Nested
    @DisplayName("프로필 삭제")
    class DeleteProfile {

        @Test
        @DisplayName("204")
        void deleteProfile_204() throws Exception {
            // given
            NormalUser normalUser = NormalUser.builder()
                    .email("testtest@gmail.com")
                    .password(passwordEncoder.encode("testPassword"))
                    .build();
            normalUser = normalUserRepository.save(normalUser);

            User user = User.builder()
                    .normalUser(normalUser)
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(delete("/user/profile"));

            // then
            resultActions.andExpect(status().is(204));
        }

    }

    @Nested
    @DisplayName("유저 정보 얻기")
    class GetUserInfo {

        @Test
        @DisplayName("200")
        void getUserInfo_200() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(get("/user/info"));

            // then
            resultActions.andExpect(status().is(200));
        }

    }

    @Nested
    @DisplayName("유저 정보 변경")
    class ChangeUserInfo {

        private UserInfoChangeRequest makeRequest(List<Integer> field, String introduce, String name) throws NoSuchFieldException, IllegalAccessException {
            UserInfoChangeRequest request = new UserInfoChangeRequest();
            inputField(request, "field", field);
            inputField(request, "introduce", introduce);
            inputField(request, "name", name);
            return request;
        }

        @Test
        @DisplayName("200")
        void changeUserInfo_200() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            FieldKind fieldKind = FieldKind.builder()
                    .content("백엔드")
                    .build();
            fieldKind = fieldKindRepository.save(fieldKind);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());
            List<Integer> field = new ArrayList<>();
            field.add(fieldKind.getPk());
            UserInfoChangeRequest request = makeRequest(field, "바람이 불지 않으면 노를 저어라", "김땡땡");

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/info"), request);

            // then
            resultActions.andExpect(status().is(200));
        }

        @Test
        @DisplayName("400")
        void changeUserInfo_400() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());
            UserInfoChangeRequest request = makeRequest(null, null, "바람이 불지 않으면 노를 저어라 김땡땡");

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/info"), request);

            // then
            resultActions.andExpect(status().is(400));
        }

        @Test
        @DisplayName("404")
        void changeUserInfo_404() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());
            List<Integer> field = new ArrayList<>();
            field.add(1);
            UserInfoChangeRequest request = makeRequest(field, "바람이 불지 않으면 노를 저어라", "김땡땡");

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/info"), request);

            // then
            resultActions.andExpect(status().is(404));
        }

    }

    @Nested
    @DisplayName("회원 탈퇴")
    class deleteUser {

        @Test
        @DisplayName("204")
        void deleteUser_204() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            Notification notification = Notification.builder()
                    .toUser(user)
                    .isRead(false)
                    .type(NoticeType.COMMENT)
                    .fromUser(user)
                    .build();
            notificationRepository.save(notification);

            Portfolio portfolio = Portfolio.builder()
                    .user(user)
                    .title("제목제목")
                    .introduce("나는 노를 젓는 김땡땡")
                    .date(LocalDate.now())
                    .isOpen(true)
                    .build();
            portfolioRepository.save(portfolio);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(delete("/user"));

            // then
            resultActions.andExpect(status().is(204));
        }

    }

    @Nested
    @DisplayName("터칭한 포트폴리오 가져오기")
    class getTouchingPortfolio {

        @Test
        @DisplayName("200")
        void getTouchingPortfolio_200() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            Portfolio portfolio = Portfolio.builder()
                    .user(user)
                    .title("제목제목")
                    .introduce("나는 노를 젓는 김땡땡")
                    .date(LocalDate.now())
                    .isOpen(true)
                    .build();
            portfolio = portfolioRepository.save(portfolio);

            Touching touching = Touching.builder()
                    .id(new TouchingId(user.getPk(), portfolio.getPk()))
                    .user(user)
                    .portfolio(portfolio)
                    .build();
            touchingRepository.save(touching);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(get("/user/touching?size=1&page=0"));

            // then
            resultActions.andExpect(status().is(200));
        }

        @Test
        @DisplayName("400")
        void getTouchingPortfolio_400() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(get("/user/touching"));

            // then
            resultActions.andExpect(status().is(400));
        }

    }

    @Nested
    @DisplayName("나의 포트폴리오 가져오기")
    class getMyPortfolio {

        @Test
        @DisplayName("200")
        void getMyPortfolio_200() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            Portfolio portfolio = Portfolio.builder()
                    .user(user)
                    .title("제목제목")
                    .introduce("나는 노를 젓는 김땡땡")
                    .date(LocalDate.now())
                    .isOpen(true)
                    .build();
            portfolioRepository.save(portfolio);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(get("/user/portfolio"));

            // then
            resultActions.andExpect(status().is(200));
        }

    }

    @Nested
    @DisplayName("알림 가져오기")
    class getNotification {

        @Test
        @DisplayName("200")
        void getNotification_200() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            user = userRepository.save(user);

            Notification notification = Notification.builder()
                    .toUser(user)
                    .isRead(false)
                    .type(NoticeType.COMMENT)
                    .fromUser(user)
                    .build();
            notificationRepository.save(notification);

            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(get("/user/notification"));

            // then
            resultActions.andExpect(status().is(200));
        }

    }

}
