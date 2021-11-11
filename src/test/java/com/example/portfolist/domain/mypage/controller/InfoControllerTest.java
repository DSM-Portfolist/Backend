package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InfoControllerTest extends ApiTest {

    @Nested
    @DisplayName("유저 프로필 가져오기")
    class GetProfile {

        @Test
        @DisplayName("200")
        void getProfile_200() throws Exception {
            // given
            User user = User.builder()
                    .githubId("githubUser")
                    .name("가나다")
                    .build();
            userRepository.save(user);

            // when
            ResultActions resultActions = requestMvc(get("/info/user/" + user.getPk()));

            // then
            resultActions.andExpect(status().is(200));

        }

    }

}
