package com.example.portfolist.domain.mypage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {

    @NotBlank
    private String nowPassword;

    @NotBlank
    private String newPassword;

}
