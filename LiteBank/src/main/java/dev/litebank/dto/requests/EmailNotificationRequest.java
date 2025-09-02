package dev.litebank.dto.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmailNotificationRequest {
    private String recipient;
    private String subject;
    private String mailBody;
    private Sender sender;
    private List<Recipient> to;
    private String htmlContent;

    public EmailNotificationRequest() {
        sender = new Sender("litebank", "yetuns1122@gmail.com");
    }
}