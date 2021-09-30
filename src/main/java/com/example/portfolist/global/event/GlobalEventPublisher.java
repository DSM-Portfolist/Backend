package com.example.portfolist.global.event;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.NoticeType;
import com.example.portfolist.global.event.event.EmailEvent;
import com.example.portfolist.global.event.event.NoticeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void makeNotice(User fromUser, User toUser, NoticeType event) {
        eventPublisher.publishEvent(new NoticeEvent(fromUser, toUser, event));
    }

    public void sendEmail(String toEmail, String title, String content) {
        eventPublisher.publishEvent(new EmailEvent(toEmail, title, content));
    }

}
