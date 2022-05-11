package com.mak.demo.poll.manager.port.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDomain implements Serializable {

  private Long id;
  private LocalDateTime updatedAt;
  private Long version = 0L;
  private LocalDateTime createdAt;
  private String createdBy;
  private String updatedBy;
  private LocalDateTime deletedAt;

}
