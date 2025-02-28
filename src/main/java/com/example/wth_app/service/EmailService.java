package com.example.wth_app.service;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
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
    @Value("${mailgun.api.key}")
    private String apiKey;
    @Value("${mailgun.from.email}")
    private String fromEmail;

    public void sendWeatherEmail(String to, String subject, String body) {
        try {
            MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(apiKey)
                    .createApi(MailgunMessagesApi.class);

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
