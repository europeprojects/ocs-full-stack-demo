package com.mak.demo.poll.manager.app.mapper;

import com.mak.demo.poll.manager.app.model.dto.ChoiceDto;
import com.mak.demo.poll.manager.port.domain.Choice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChoiceDTOMapper extends BaseMapper<Choice, ChoiceDto> {

}