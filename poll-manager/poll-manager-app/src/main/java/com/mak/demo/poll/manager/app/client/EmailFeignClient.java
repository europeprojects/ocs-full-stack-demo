package com.mak.demo.poll.manager.app.client;

import com.mak.demo.poll.manager.app.model.email.domain.EmailRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-manager",url="${notification-manager.ribbon.listOfServers}")
public interface EmailFeignClient {

    @PostMapping("/api/v1/email-notification/send")
    ResponseEntity<Void> sendEmailNotification(EmailRequest cmsServiceUsage);

}
