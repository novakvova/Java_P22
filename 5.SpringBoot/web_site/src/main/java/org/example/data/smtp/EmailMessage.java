package org.example.data.smtp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {
    private String subject;
    private String body;
    private String to;

    public EmailMessage() {}

    public EmailMessage(String subject, String body, String to) {
        this.subject = subject;
        this.body = body;
        this.to = to;
    }
}