package com.example.portfolist.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileGetResponse {

    private String profile;
    private boolean githubUser;

}
