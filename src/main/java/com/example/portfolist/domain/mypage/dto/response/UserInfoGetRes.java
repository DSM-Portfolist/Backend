package com.example.portfolist.domain.mypage.dto.response;

import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoGetRes {


    @Getter
    @AllArgsConstructor
    public static class UserInfoGetFieldResponse {

        private int id;
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PRIVATE)
    public static class UserInfoGetResponse {

        private List<UserInfoGetFieldResponse> field;
        private String introduce;
        private String name;

        public static UserInfoGetResponse from(User user) {
            List<UserInfoGetFieldResponse> fields = user.getFieldList().stream()
                    .map(field -> {
                        FieldKind fieldKind = field.getFieldKind();
                        return new UserInfoGetFieldResponse(fieldKind.getPk(), fieldKind.getContent());
                    })
                    .collect(Collectors.toList());

            return UserInfoGetResponse.builder()
                    .field(fields)
                    .introduce(user.getIntroduce())
                    .name(user.getName())
                    .build();
        }
    }

}
