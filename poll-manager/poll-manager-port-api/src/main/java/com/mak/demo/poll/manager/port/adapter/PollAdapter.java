package com.mak.demo.poll.manager.port.adapter;

import com.mak.demo.poll.manager.port.domain.Poll;
import com.mak.demo.poll.manager.port.filter.PollFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PollAdapter extends BaseAdapter<Poll, PollFilter> {

    Page<Poll> findByIsApprovedTrue(Pageable pageable);

    Optional<Poll> findByIsActiveTrueAndId(Long pollId);

    Page<Poll> findByIsActiveTrue(Pageable pageable);

    Page<Poll> findByIsApprovedFalse(Pageable pageable);

    Page<Poll> findByIsActiveFalse(Pageable pageable);

    Page<Poll> findByIsApprovedTrueAndIsActiveTrue(Pageable pageable);
}

