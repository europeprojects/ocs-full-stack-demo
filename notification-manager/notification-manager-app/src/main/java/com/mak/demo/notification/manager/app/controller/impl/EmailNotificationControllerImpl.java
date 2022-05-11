package com.mak.demo.notification.manager.app.controller.impl;


import com.mak.demo.notification.manager.app.controller.EmailNotificationController;
import com.mak.demo.notification.manager.port.email.adapter.EmailServiceAdapter;
import com.mak.demo.notification.manager.port.email.domain.EmailRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Notification Manager", description = "Notification Api")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/email-notification")
public class EmailNotificationControllerImpl implements EmailNotificationController {

	private final EmailServiceAdapter emailServiceAdapter;

	@Override
	public void sendEmail(EmailRequest request) throws Exception {
		emailServiceAdapter.sendEmail(request);
	}
}
