package com.mak.demo.notification.email.service.validator;

import com.mak.demo.notification.manager.port.email.domain.EmailRequest;

import javax.mail.MessagingException;


public interface EmailValidator {
    
   void validateMail(EmailRequest request) throws MessagingException;
   
}
