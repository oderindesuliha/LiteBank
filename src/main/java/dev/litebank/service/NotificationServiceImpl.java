package dev.litebank.service;

import dev.litebank.dto.requests.EmailNotificationRequest;
import dev.litebank.dto.responses.EmailNotificationResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private EmailNotificationService emailNotificationService;


    @Override
    public EmailNotificationResponse notifyBy(EmailNotificationRequest notificationRequest) throws IOException {
        return emailNotificationService.notifyBy(notificationRequest);
    }

}

