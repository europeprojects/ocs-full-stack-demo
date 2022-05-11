package com.mak.demo.poll.manager.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.mak.demo.poll.manager.rdms")
@EntityScan(basePackages="com.mak.demo.poll.manager.rdms")
public class RepositoryConfiguration {

}
