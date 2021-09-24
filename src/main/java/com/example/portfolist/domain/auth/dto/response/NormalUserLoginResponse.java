package com.example.portfolist.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NormalUserLoginResponse {

    private String accessToken;
    private String refreshToken;

}
