package com.mak.demo.poll.manager.port.filter;

import lombok.Data;

@Data
public class VoteFilter extends BaseFilter{

    private Long pollId;
    private Long choiceId;
    private Long userId;

}
