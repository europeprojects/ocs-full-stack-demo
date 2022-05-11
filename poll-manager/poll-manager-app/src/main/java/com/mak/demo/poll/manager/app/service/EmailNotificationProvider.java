package com.mak.demo.poll.manager.app.service;

import com.mak.demo.poll.manager.app.model.email.domain.EmailRequest;
import com.mak.demo.poll.manager.port.domain.Poll;

public interface EmailNotificationProvider {

    void sendNotificationIfConfirmedPoll(Poll poll);

    void sendNotificationIfProposedPoll(Poll poll);

}
