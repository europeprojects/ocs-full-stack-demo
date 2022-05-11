package com.mak.demo.poll.manager.rdms.specification;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseFilterSpecification<BaseEntity, Filter> extends BaseSpecification {

  public abstract Specification<BaseEntity> filter(Filter filter);

}
