package com.example.portfolist.domain.mypage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoChangeRequest {

    private List<Integer> field;

    @Size(max = 40)
    private String introduce;

    @Size(max = 20)
    private String name;

}
