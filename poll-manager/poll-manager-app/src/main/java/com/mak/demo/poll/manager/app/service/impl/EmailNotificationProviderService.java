package com.mak.demo.poll.manager.app.service.impl;

import com.mak.demo.poll.manager.app.client.EmailClient;
import com.mak.demo.poll.manager.app.model.email.domain.EmailRequest;
import com.mak.demo.poll.manager.app.model.email.domain.Recipients;
import com.mak.demo.poll.manager.app.service.EmailNotificationProvider;
import com.mak.demo.poll.manager.port.domain.Poll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EmailNotificationProviderService implements EmailNotificationProvider {

    private final EmailClient emailClient;

    @Value("${notification.email.from:noreply-ocs@gmail.com}")
    private String notificationMailFrom;


    @Async
    @Override
    public void sendNotificationIfConfirmedPoll(Poll poll) {
        emailClient.sendEmailNotification(createEmailRequestIfConfirmedPoll(poll));
    }

    @Async
    @Override
    public void sendNotificationIfProposedPoll(Poll poll) {
        emailClient.sendEmailNotification(createEmailRequestIfProposedPoll(poll));
    }

    private EmailRequest createEmailRequestIfConfirmedPoll(Poll poll){

        Recipients recipients = Recipients.builder().toList(List.of(poll.getEmail())).build();
        EmailRequest request = EmailRequest.builder().subject("Poll Manager Response")
                .body(String.format("Dear %s, Congratulations!it is confirmed poll you created.",poll.getUsername())).isMailBodyHTML(false)
                .from(notificationMailFrom).recipients(recipients).build();
        return request;
    }

    private EmailRequest createEmailRequestIfProposedPoll(Poll poll){

        Recipients recipients = Recipients.builder().toList(List.of(poll.getEmail())).build();
        EmailRequest request = EmailRequest.builder().subject("Poll Manager Response")
                .body(String.format("Dear %s, it is sent your poll suggestion to our system. Thank you",poll.getUsername())).isMailBodyHTML(false)
                .from(notificationMailFrom).recipients(recipients).build();
        return request;
    }

}
