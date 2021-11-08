package com.example.portfolist.domain.auth.util.api.client;

import com.example.portfolist.domain.auth.util.api.dto.GithubCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "githubCodeClient", url = "https://github.com")
public interface GithubCodeClient {

    @PostMapping("/login/oauth/access_token")
    GithubCodeResponse getUserToken(@RequestParam("client_id") String clientId,
                                    @RequestParam("client_secret") String clientSecret,
                                    @RequestParam("code") String code);

}
