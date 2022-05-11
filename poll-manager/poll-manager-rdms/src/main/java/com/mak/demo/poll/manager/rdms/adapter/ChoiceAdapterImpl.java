package com.mak.demo.poll.manager.rdms.adapter;

import com.mak.demo.poll.manager.port.adapter.ChoiceAdapter;
import com.mak.demo.poll.manager.port.domain.Choice;
import com.mak.demo.poll.manager.port.filter.ChoiceFilter;
import com.mak.demo.poll.manager.rdms.entity.ChoiceEntity;
import com.mak.demo.poll.manager.rdms.mapper.ChoiceMapper;
import com.mak.demo.poll.manager.rdms.repository.ChoiceRepository;
import com.mak.demo.poll.manager.rdms.specification.ChoiceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChoiceAdapterImpl extends BaseAdapterImpl<ChoiceEntity, Choice, ChoiceMapper,ChoiceFilter, ChoiceRepository, ChoiceSpecification>
    implements ChoiceAdapter {


  private final ChoiceRepository choiceRepository;
  private final ChoiceMapper choiceMapper;
  private final ChoiceSpecification choiceSpecification;


  @Override
  public ChoiceRepository getRepository() {
    return choiceRepository;
  }

  @Override
  public ChoiceMapper getMapper() {
    return choiceMapper;
  }

  @Override
  protected ChoiceSpecification getSpecification() {
    return choiceSpecification;
  }

  @Override
  public void deleteByPollId(Long id) {
     //choiceRepository.deleteByPollId(id);
  }
}
