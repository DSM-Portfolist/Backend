package com.example.portfolist.domain.mypage.dto.response;

import com.example.portfolist.domain.auth.entity.FieldKind;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class FieldGetResponse {

    private int id;
    private String content;

    public static FieldGetResponse from(FieldKind fieldKind) {
        return FieldGetResponse.builder()
                .id(fieldKind.getId())
                .content(fieldKind.getContent())
                .build();
    }

}
