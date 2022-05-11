package com.mak.demo.poll.manager.rdms.adapter;

import com.mak.demo.poll.manager.port.adapter.PollAdapter;
import com.mak.demo.poll.manager.port.domain.Poll;
import com.mak.demo.poll.manager.port.filter.PollFilter;
import com.mak.demo.poll.manager.rdms.entity.PollEntity;
import com.mak.demo.poll.manager.rdms.mapper.PollMapper;
import com.mak.demo.poll.manager.rdms.repository.PollRepository;
import com.mak.demo.poll.manager.rdms.specification.PollSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PollAdapterImpl extends BaseAdapterImpl<PollEntity, Poll, PollMapper, PollFilter, PollRepository,PollSpecification>
    implements PollAdapter {

  private final PollRepository pollRepository;
  private final PollMapper pollMapper;
  private final PollSpecification pollSpecification;


  @Override
  public PollRepository getRepository() {
    return pollRepository;
  }

  @Override
  public PollMapper getMapper() {
    return pollMapper;
  }

  @Override
  protected PollSpecification getSpecification() {
    return pollSpecification;
  }

  @Override
  public Page<Poll> findByIsApprovedTrue(Pageable pageable) {
    return pollRepository.findByIsApprovedTrue(pageable).map(pollMapper::toDomainObject);
  }

  @Override
  public Optional<Poll> findByIsActiveTrueAndId(Long pollId) {
    return pollRepository.findByIsActiveTrueAndId(pollId).map(pollMapper::toDomainObject);
  }

  @Override
  public Page<Poll> findByIsActiveTrue(Pageable pageable) {
    return pollRepository.findByIsActiveTrue(pageable).map(pollMapper::toDomainObject);
  }

  @Override
  public Page<Poll> findByIsApprovedFalse(Pageable pageable) {
    return pollRepository.findByIsApprovedFalse(pageable).map(pollMapper::toDomainObject);
  }

  @Override
  public Page<Poll> findByIsActiveFalse(Pageable pageable) {
    return pollRepository.findByIsActiveFalse(pageable).map(pollMapper::toDomainObject);
  }

  @Override
  public Page<Poll> findByIsApprovedTrueAndIsActiveTrue(Pageable pageable) {
    return pollRepository.findByIsApprovedTrueAndIsActiveTrue(pageable).map(pollMapper::toDomainObject);
  }
}
