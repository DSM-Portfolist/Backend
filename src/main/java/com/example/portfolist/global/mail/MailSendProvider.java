package com.example.portfolist.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
@RequiredArgsConstructor
public class MailSendProvider {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    public void sendEmail(String toEmail, String title, String content) {

        MimeMessage message = mailSender.createMimeMessage();

        try {

            Multipart mParts = new MimeMultipart();
            MimeBodyPart mTextPart = new MimeBodyPart();

            mTextPart.setText(content, "UTF-8", "html");
            mParts.addBodyPart(mTextPart);

            message.setFrom(new InternetAddress(fromEmail, "PORTFOLIST"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(title);
            message.setContent(mParts);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        mailSender.send(message);
    }
}
