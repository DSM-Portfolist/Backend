package com.example.portfolist.domain.auth.util.api.client;

import com.example.portfolist.domain.auth.util.api.dto.GithubResponse;
import com.google.common.net.HttpHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "githubClient", url = "https://api.github.com")
public interface GithubClient {

    @GetMapping("/user")
    GithubResponse getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
