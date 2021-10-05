package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.global.file.FileUploadProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

            PasswordChangeRequest request = makeRequest("testPassword", "new");
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/password"), request);

            // then
            resultActions.andExpect(status().is(200))
                    .andDo(print());
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
            resultActions.andExpect(status().is(400))
                    .andDo(print());
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

            PasswordChangeRequest request = makeRequest("testPassword", "new");
            String token = jwtTokenProvider.generateAccessToken(user.getPk());

            // when
            setToken(token);
            ResultActions resultActions = requestMvc(patch("/user/password"), request);

            // then
            resultActions.andExpect(status().is(401))
                    .andDo(print());
        }

    }

}
