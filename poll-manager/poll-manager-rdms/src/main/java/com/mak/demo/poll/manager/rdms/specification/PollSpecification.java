package com.mak.demo.poll.manager.rdms.specification;

import com.mak.demo.poll.manager.port.filter.PollFilter;
import com.mak.demo.poll.manager.rdms.entity.PollEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PollSpecification extends BaseFilterSpecification<PollEntity, PollFilter> {

  @Override
  public Specification<PollEntity> filter(PollFilter filter) {
    return null;
  }
}
