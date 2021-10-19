package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.redis.Certification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmailControllerTest extends ApiTest {

    @Nested
    @DisplayName("인증 버튼")
    class ButtonClick {

        @Test
        @DisplayName("SUCCESS")
        void buttonClick_success() throws Exception {
            // given
            Certification certification = Certification.builder()
                    .id(1L)
                    .email("testtest@gmail.com")
                    .token("token")
                    .certification(false)
                    .exp(200000L)
                    .build();
            given(certificationRepository.findByToken("token")).willReturn(Optional.of(certification));

            // when
            ResultActions resultActions = requestMvc(get("/receive?token=token"));

            // then
            resultActions.andExpect(status().is(302));
        }

        @Test
        @DisplayName("FAIL")
        void buttonClick_fail() throws Exception {
            // given
            given(certificationRepository.findByToken("token")).willReturn(Optional.empty());

            // when
            ResultActions resultActions = requestMvc(get("/receive?token=token"));

            // then
            resultActions.andExpect(status().is(302));
        }

    }

}
