//package dev.litebank.service;
//
//import dev.litebank.dto.requests.EmailNotificationRequest;
//import dev.litebank.dto.responses.BrevoMailResponse;
//import dev.litebank.dto.responses.EmailNotificationResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.List;
//
//@Service
//@Primary
//@Slf4j
//@RequiredArgsConstructor
//public class BrevoMailService implements EmailNotificationService{
//    @Value("${mail.api.key}")
//    private String apiKey;
//    @Value("${mail.api.uri}")
//    private String brevoApiUrl;
//    private final RestTemplate restTemplate;
//
//    @Override
//    public EmailNotificationResponse notifyBy(EmailNotificationRequest notificationRequest) throws IOException {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
//        httpHeaders.add("api-key", apiKey);
//
//        RequestEntity<EmailNotificationRequest> request =
//                new RequestEntity<>(notificationRequest,httpHeaders, HttpMethod.POST, URI.create(brevoApiUrl));
//        BrevoMailResponse response =
//                restTemplate.postForEntity(brevoApiUrl, request, BrevoMailResponse.class, httpHeaders).getBody();
//        EmailNotificationResponse emailNotificationResponse = new EmailNotificationResponse();
//        assert response != null;
//        if (response.getMessageId() !=null) {
//            emailNotificationResponse.setStatus(true);
//            return emailNotificationResponse;
//        }
//        emailNotificationResponse.setStatus(false);
//        return emailNotificationResponse;
//    }
//}