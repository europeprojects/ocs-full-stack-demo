package com.mak.demo.poll.manager.rdms.mapper;

import com.mak.demo.poll.manager.port.domain.Choice;
import com.mak.demo.poll.manager.rdms.entity.ChoiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChoiceMapper extends BaseMapper<ChoiceEntity, Choice> {
    @Override
    Choice toDomainObject(ChoiceEntity e);

    @Override
    ChoiceEntity toEntity(Choice d);
}