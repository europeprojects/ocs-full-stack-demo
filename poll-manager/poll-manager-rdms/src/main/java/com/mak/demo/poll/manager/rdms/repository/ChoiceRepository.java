package com.mak.demo.poll.manager.rdms.repository;

import com.mak.demo.poll.manager.rdms.entity.ChoiceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChoiceRepository extends BaseRepository<ChoiceEntity> {

/*    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Choice ch WHERE ch.poll.id = ?1")
    int deleteByPollId(Long id);*/

}
