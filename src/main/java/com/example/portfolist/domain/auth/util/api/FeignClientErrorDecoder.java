package com.example.portfolist.domain.auth.util.api;

import com.example.portfolist.domain.auth.exception.OauthServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OauthServerException decode(final String methodKey, Response response) {

        String message = "Server failed to request other server.";

        if (response.body() != null) {
            try {
                message = objectMapper.readTree(response.body().toString())
                        .get("message").asText();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return new OauthServerException(response.status(), message);
    }

}
