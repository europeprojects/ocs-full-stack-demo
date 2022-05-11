package com.mak.demo.poll.manager.rdms.specification;

import com.mak.demo.poll.manager.port.filter.ChoiceFilter;
import com.mak.demo.poll.manager.rdms.entity.ChoiceEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ChoiceSpecification extends BaseFilterSpecification<ChoiceEntity, ChoiceFilter> {

  @Override
  public Specification<ChoiceEntity> filter(ChoiceFilter filter) {
    return null;
  }
}
