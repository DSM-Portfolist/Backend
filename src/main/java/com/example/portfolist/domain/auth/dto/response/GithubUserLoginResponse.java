package com.example.portfolist.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GithubUserLoginResponse {

    private String accessToken;
    private String refreshToken;

}
