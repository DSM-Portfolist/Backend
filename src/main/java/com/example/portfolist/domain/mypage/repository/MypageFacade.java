package com.example.portfolist.domain.mypage.repository;

import com.example.portfolist.domain.mypage.entity.Notification;
import com.example.portfolist.domain.mypage.repository.repository.NotificationRepository;
import com.example.portfolist.global.event.event.NoticeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MypageFacade {

    private final NotificationRepository notificationRepository;

    public void save(NoticeEvent event) {
        Notification notification = Notification.builder()
                .toUser(event.getToUser())
                .isRead(false)
                .type(event.getType())
                .fromUser(event.getFromUser())
                .build();
        notificationRepository.save(notification);
    }

}
