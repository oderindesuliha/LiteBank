package dev.litebank.service;

import dev.litebank.dto.requests.EmailNotificationRequest;
import dev.litebank.dto.responses.EmailNotificationResponse;

import java.io.IOException;

public interface EmailNotificationService {
    EmailNotificationResponse notifyBy(EmailNotificationRequest notificationRequest) throws IOException;

}
