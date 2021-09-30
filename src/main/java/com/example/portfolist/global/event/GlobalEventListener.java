package com.example.portfolist.global.event;

import com.example.portfolist.domain.mypage.repository.MypageFacade;
import com.example.portfolist.global.event.event.EmailEvent;
import com.example.portfolist.global.event.event.NoticeEvent;
import com.example.portfolist.global.mail.MailSendProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalEventListener {

    private final MailSendProvider mailSendProvider;
    private final MypageFacade mypageFacade;

    @Async
    @EventListener
    public void saveNotice(NoticeEvent event) {
        mypageFacade.save(event);
    }

    @EventListener
    public void sendEmail(EmailEvent event) {
        mailSendProvider.sendEmail(event.getToEmail(), event.getTitle(), event.getContent());
    }

}
