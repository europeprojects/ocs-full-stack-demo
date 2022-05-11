package com.mak.demo.poll.manager.app.mapper;

import com.mak.demo.poll.manager.app.model.dto.PollDto;
import com.mak.demo.poll.manager.port.domain.Poll;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PollDTOMapper extends BaseMapper<Poll, PollDto> {

}