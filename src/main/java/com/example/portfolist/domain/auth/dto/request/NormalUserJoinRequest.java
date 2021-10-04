package com.example.portfolist.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class NormalUserJoinRequest {

    @NotBlank
    @Size(max = 20)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 12)
    private String password;

    @NotBlank
    private List<Integer> field;

}
