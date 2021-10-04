package com.example.portfolist.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class NormalUserLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 12)
    private String password;

}
