package com.mak.demo.poll.manager.app.mapper;

import com.mak.demo.poll.manager.app.model.dto.VoteDto;
import com.mak.demo.poll.manager.port.domain.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteDTOMapper extends BaseMapper<Vote, VoteDto> {

}