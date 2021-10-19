package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.FieldKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FieldControllerTest extends ApiTest {

    @Nested
    @DisplayName("분야 가져오기")
    class GetField {

        @Test
        @DisplayName("200")
        void getField() throws Exception {
            // given
            FieldKind fieldKind = FieldKind.builder()
                    .content("백엔드")
                    .build();
            fieldKindRepository.save(fieldKind);

            // when
            ResultActions resultActions = requestMvc(get("/field"));

            // then
            resultActions.andExpect(status().is(200));
        }

    }

}
