package com.mak.demo.poll.manager.rdms.specification;

import com.mak.demo.poll.manager.port.filter.VoteFilter;
import com.mak.demo.poll.manager.rdms.entity.VoteEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VoteSpecification extends BaseFilterSpecification<VoteEntity, VoteFilter> {

  @Override
  public Specification<VoteEntity> filter(VoteFilter filter) {
    return null;
  }
}
