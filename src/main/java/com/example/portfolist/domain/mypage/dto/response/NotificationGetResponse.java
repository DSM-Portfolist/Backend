package com.example.portfolist.domain.mypage.dto.response;

import com.example.portfolist.domain.mypage.entity.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
public class NotificationGetResponse {

    private long id;
    private String name;
    private String type;

    public static NotificationGetResponse from(Notification notification) {
        return NotificationGetResponse.builder()
                .id(notification.getPk())
                .name(notification.getFromUser().getName())
                .type(notification.getType().name())
                .build();
    }

}
