package com.mak.demo.poll.manager.app.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto extends BaseDTO {

    private PollDto poll;
    private ChoiceDto choice;


}
