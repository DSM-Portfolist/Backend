package com.example.portfolist.domain.mypage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationStatusChangeRequest {

    @NotNull
    private Boolean notification;

}
