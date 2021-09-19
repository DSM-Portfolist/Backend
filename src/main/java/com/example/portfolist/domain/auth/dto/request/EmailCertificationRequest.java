package com.example.portfolist.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class EmailCertificationRequest {

    @Email
    private String email;

}
