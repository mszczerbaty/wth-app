package com.example.wth_app.service;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Value("${mailgun.domain}")
    private String domain;
    @Value("${mailgun.from.email}")
    private String fromEmail;
    private final MailgunMessagesApi mailgunMessagesApi;

    public void sendWeatherEmail(String to, String subject, String body) {
        try {
            Message message = Message.builder()
                    .from(fromEmail)
                    .to(to)
                    .subject(subject)
                    .text(body)
                    .build();

            MessageResponse response = mailgunMessagesApi.sendMessage(domain, message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email", e);
            throw new RuntimeException("Failed to send the mail", e);
        }
    }
}
