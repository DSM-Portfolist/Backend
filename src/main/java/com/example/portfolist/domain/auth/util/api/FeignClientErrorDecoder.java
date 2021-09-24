package com.example.portfolist.domain.auth.util.api;

import com.example.portfolist.domain.auth.exception.OauthServerException;
import com.example.portfolist.domain.auth.util.api.dto.GithubErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    private final StringDecoder stringDecoder = new StringDecoder();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OauthServerException decode(final String methodKey, Response response) {
        String message = "Server failed to request oauth server.";

        if (response.body() != null) {
            try {
                String json = stringDecoder.decode(response, String.class).toString();
                message = objectMapper.readValue(json, GithubErrorResponse.class).getMessage();
            } catch (IOException e) {
                log.error(methodKey + "Error Deserializing response body from failed feign request response.", e);
            }
        }

        return new OauthServerException(response.status(), message);
    }

}
