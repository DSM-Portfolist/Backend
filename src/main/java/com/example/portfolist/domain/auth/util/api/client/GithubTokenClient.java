package com.example.portfolist.domain.auth.util.api.client;

import com.example.portfolist.domain.auth.util.api.dto.GithubTokenResponse;
import com.google.common.net.HttpHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "githubTokenClient", url = "https://api.github.com")
public interface GithubTokenClient {

    @GetMapping("/user")
    GithubTokenResponse getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
