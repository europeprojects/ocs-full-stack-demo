package com.mak.demo.notification.manager.app.controller;


import com.mak.demo.notification.manager.port.email.domain.EmailRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;




public interface EmailNotificationController {

    @PostMapping(value="/send",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void sendEmail(@RequestBody EmailRequest request) throws Exception;

}
