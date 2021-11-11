package com.example.portfolist.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileGetResponse {

    private String profileImg;
    private boolean githubUser;

}
