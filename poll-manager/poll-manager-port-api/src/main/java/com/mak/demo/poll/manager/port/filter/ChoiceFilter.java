package com.mak.demo.poll.manager.port.filter;

import lombok.Data;

@Data
public class ChoiceFilter extends BaseFilter{

    private Long id;
    private String text;
    private Long pollId;

}
