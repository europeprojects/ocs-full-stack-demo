package com.mak.demo.poll.manager.port.filter;

import lombok.Data;

@Data
public abstract class BaseFilter {

  private int pageNumber;
  private int pageItemCount;
  private int totalItemCount;
  private String sortProperty;
  private boolean sortAsc;

}
