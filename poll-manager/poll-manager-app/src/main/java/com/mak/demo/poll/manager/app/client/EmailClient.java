package com.mak.demo.poll.manager.app.client;

import com.mak.demo.poll.manager.app.model.email.domain.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class EmailClient {

   private final EmailFeignClient emailFeignClient;

   public void sendEmailNotification(EmailRequest request){
       emailFeignClient.sendEmailNotification(request);
   }

}
