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
public class InfoUserGetResponse {private List<String> field;
    private String introduce;
    private String name;
    private String profileImg;

    public static InfoUserGetResponse from(User user) {
        List<String> fields = null;
        if(!CollectionUtils.isNullOrEmpty(user.getFieldList())) {
            fields = user.getFieldList().stream()
                    .map(field -> {
                        return field.getFieldKind().getContent();
                    })
                    .collect(Collectors.toList());
        }

        return InfoUserGetResponse.builder()
                .field(fields)
                .introduce(user.getIntroduce())
                .name(user.getName())
                .profileImg(user.getUrl())
                .build();
    }

}
