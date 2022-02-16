package com.example.portfolist.domain.mypage.repository;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.Notification;
import com.example.portfolist.domain.mypage.repository.repository.NotificationRepository;
import com.example.portfolist.global.event.event.NoticeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
                .portfolio(event.getPortfolio())
                .build();
        notificationRepository.save(notification);
    }

    public void deleteAlreadyReadNotification(User user) {
        notificationRepository.deleteByToUserAndIsReadIsTrue(user);
    }

    public List<Notification> findNotificationByUser(User user) {
        return notificationRepository.findByToUser(user);
    }

    public void deleteNotificationByUser(User user) {
        notificationRepository.deleteByToUser(user);
        notificationRepository.deleteByFromUser(user);
    }

}
