package com.example.portfolist.domain.mypage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {

    @NotBlank
    @Size(min = 4, max = 12)
    private String nowPassword;

    @NotBlank
    @Size(min = 4, max = 12)
    private String newPassword;

}
