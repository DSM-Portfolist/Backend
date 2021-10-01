package com.example.portfolist.domain.mypage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoChangeRequest {

    private List<Integer> field;

    private String introduce;

    private String name;

}
