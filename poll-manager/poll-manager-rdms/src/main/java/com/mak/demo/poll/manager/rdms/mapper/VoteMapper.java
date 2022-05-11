package com.mak.demo.poll.manager.rdms.mapper;

import com.mak.demo.poll.manager.port.domain.Poll;
import com.mak.demo.poll.manager.port.domain.Vote;
import com.mak.demo.poll.manager.rdms.entity.PollEntity;
import com.mak.demo.poll.manager.rdms.entity.VoteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper extends BaseMapper<VoteEntity, Vote> {

    @Override
    Vote toDomainObject(VoteEntity e);

    @Override
    VoteEntity toEntity(Vote d);

}