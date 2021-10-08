package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.ServiceTest;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.mypage.dto.response.FieldGetResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FieldServiceTest extends ServiceTest {

    @Mock
    private AuthFacade authFacade;

    @InjectMocks
    private FieldService fieldService;

    @Nested
    @DisplayName("Get Field")
    class GetField {

        @Test
        @DisplayName("Success")
        void getField_Success() {
            // given
            FieldKind fieldKind = FieldKind.builder()
                    .pk(1)
                    .content("백엔드")
                    .build();

            List<FieldKind> fieldKindList = new ArrayList<>();
            fieldKindList.add(fieldKind);

            given(authFacade.findFieldKindAll()).willReturn(fieldKindList);

            // when
            List<FieldGetResponse> responses = fieldService.getField();

            // then
            verify(authFacade, times(1)).findFieldKindAll();
            Assertions.assertEquals(responses.size(), 1);
        }

    }

}
