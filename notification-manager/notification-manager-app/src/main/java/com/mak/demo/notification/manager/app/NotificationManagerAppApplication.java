package com.mak.demo.notification.manager.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableFeignClients
@EnableWebMvc
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.mak.demo.notification.*"},exclude = {DataSourceAutoConfiguration.class })
public class NotificationManagerAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationManagerAppApplication.class, args);

  }
}
