package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long userId;

    private String name;

    private String profileImg;

    public static UserDto of(User user) {
        return UserDto.builder()
                .userId(user.getPk())
                .name(user.getName())
                .profileImg(user.getUrl())
                .build();
    }
}
