package com.mak.demo.poll.manager.rdms.mapper;

import com.mak.demo.poll.manager.port.domain.Choice;
import com.mak.demo.poll.manager.port.domain.Poll;
import com.mak.demo.poll.manager.rdms.entity.ChoiceEntity;
import com.mak.demo.poll.manager.rdms.entity.PollEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PollMapper extends BaseMapper<PollEntity, Poll> {

    @Override
    Poll toDomainObject(PollEntity e);

    @Override
    PollEntity toEntity(Poll d);

}