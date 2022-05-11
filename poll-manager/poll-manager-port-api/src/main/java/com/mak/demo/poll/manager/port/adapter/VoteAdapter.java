package com.mak.demo.poll.manager.port.adapter;

import com.mak.demo.poll.manager.port.domain.Vote;
import com.mak.demo.poll.manager.port.domain.projection.ChoiceVotes;
import com.mak.demo.poll.manager.port.filter.VoteFilter;

import java.util.Collection;
import java.util.List;

public interface VoteAdapter extends BaseAdapter<Vote, VoteFilter> {

    List<ChoiceVotes> countByPollIdInGroupByChoiceId(List<Long> pollIds);

    List<Vote>  findByUserIdAndPollIdIn(String username, List<Long> pollIds);

    void deleteByPollId(Long id);
}

