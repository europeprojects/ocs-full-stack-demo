package com.mak.demo.notification.manager.port.email.adapter;


import com.mak.demo.notification.manager.port.email.domain.EmailRequest;



public interface EmailServiceAdapter {
	 void sendEmail(EmailRequest request) throws Exception;
}
