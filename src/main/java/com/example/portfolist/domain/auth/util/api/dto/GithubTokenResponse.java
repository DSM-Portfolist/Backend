package com.example.portfolist.domain.auth.util.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GithubTokenResponse {

    private String login;
    private String avatarUrl;
    private String name;

}
