package com.mak.demo.poll.manager.rdms.repository;

import com.mak.demo.poll.manager.rdms.entity.PollEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRepository extends BaseRepository<PollEntity> {

    Page<PollEntity> findByIsApprovedTrue(Pageable pageable);
    Optional<PollEntity> findByIsActiveTrueAndId(Long pollId);
    Page<PollEntity> findByIsActiveTrue(Pageable pageable);
    Page<PollEntity> findByIsActiveFalse(Pageable pageable);
    Page<PollEntity> findAll(Pageable pageable);
    Page<PollEntity> findByIsActiveTrueAndIsApprovedTrue(Pageable pageable);
    Page<PollEntity> findByIsApprovedFalse(Pageable pageable);
    Page<PollEntity> findByIsApprovedTrueAndIsActiveTrue(Pageable pageable);

}
