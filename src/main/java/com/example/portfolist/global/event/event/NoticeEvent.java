package com.example.portfolist.global.event.event;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeEvent {

    private final User fromUser;
    private final User toUser;
    private final NoticeType type;

}
