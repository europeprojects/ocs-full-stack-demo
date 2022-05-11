package com.mak.demo.poll.manager.app.model.dto;

import java.io.Serializable;


public class BaseDTO implements Serializable {

  private Long version;

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
