package com.mak.demo.poll.manager.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@EnableWebMvc
@EnableFeignClients
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.mak.demo.poll.manager"})
public class PollManagerAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(PollManagerAppApplication.class, args);

  }
}
