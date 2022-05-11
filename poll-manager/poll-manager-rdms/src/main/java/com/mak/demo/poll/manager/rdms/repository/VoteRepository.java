package com.mak.demo.poll.manager.rdms.repository;

import com.mak.demo.poll.manager.port.domain.projection.ChoiceVotes;
import com.mak.demo.poll.manager.rdms.entity.VoteEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<VoteEntity> {

    @Query("SELECT v.choice.id as choiceId, count(v.id) as voteCount FROM Vote v " +
            "WHERE v.poll.id in :pollIds GROUP BY v.choice.id")
    List<ChoiceVotes> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);


    @Query("SELECT v FROM Vote v where v.username = :username and v.poll.id in :pollIds")
    List<VoteEntity> findByUserIdAndPollIdIn(@Param("username") String username, @Param("pollIds") List<Long> pollIds);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Vote st WHERE st.poll.id = ?1")
    int deleteByPollId(Long id);

}
