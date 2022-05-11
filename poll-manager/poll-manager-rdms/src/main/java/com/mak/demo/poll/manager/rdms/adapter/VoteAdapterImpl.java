package com.mak.demo.poll.manager.rdms.adapter;

import com.mak.demo.poll.manager.port.adapter.VoteAdapter;
import com.mak.demo.poll.manager.port.domain.Vote;
import com.mak.demo.poll.manager.port.domain.projection.ChoiceVotes;
import com.mak.demo.poll.manager.port.filter.VoteFilter;
import com.mak.demo.poll.manager.rdms.entity.VoteEntity;
import com.mak.demo.poll.manager.rdms.mapper.VoteMapper;
import com.mak.demo.poll.manager.rdms.repository.VoteRepository;
import com.mak.demo.poll.manager.rdms.specification.VoteSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class VoteAdapterImpl extends BaseAdapterImpl<VoteEntity, Vote, VoteMapper, VoteFilter, VoteRepository, VoteSpecification>
    implements VoteAdapter {

  private final VoteRepository voteRepository;
  private final VoteMapper voteMapper;
  private final VoteSpecification voteSpecification;


  @Override
  public VoteRepository getRepository() {
    return voteRepository;
  }

  @Override
  public VoteMapper getMapper() {
    return voteMapper;
  }

  @Override
  protected VoteSpecification getSpecification() {
    return voteSpecification;
  }

  @Override
  public List<ChoiceVotes> countByPollIdInGroupByChoiceId(List<Long> pollIds) {
    return voteRepository.countByPollIdInGroupByChoiceId(pollIds);
  }

  @Override
  public List<Vote> findByUserIdAndPollIdIn(String username, List<Long> pollIds) {
    return voteRepository.findByUserIdAndPollIdIn(username,pollIds).stream().map(voteMapper::toDomainObject).collect(Collectors.toList());
  }

  @Override
  public void deleteByPollId(Long id) {
    voteRepository.deleteByPollId(id);
  }


}
