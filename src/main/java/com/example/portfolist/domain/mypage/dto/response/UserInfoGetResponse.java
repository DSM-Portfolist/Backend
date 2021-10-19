package com.example.portfolist.domain.mypage.dto.response;

import com.amazonaws.util.CollectionUtils;
import com.example.portfolist.domain.auth.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserInfoGetResponse {

    private List<String> field;
    private String introduce;
    private String name;

    public static UserInfoGetResponse from(User user) {
        List<String> fields = null;
        if(!CollectionUtils.isNullOrEmpty(user.getFieldList())) {
             fields = user.getFieldList().stream()
                    .map(field -> {
                        return field.getFieldKind().getContent();
                    })
                    .collect(Collectors.toList());
        }

        return UserInfoGetResponse.builder()
                .field(fields)
                .introduce(user.getIntroduce())
                .name(user.getName())
                .build();
    }

}
