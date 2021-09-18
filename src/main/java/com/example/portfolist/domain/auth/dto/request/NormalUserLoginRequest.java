package com.example.portfolist.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class NormalUserLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
