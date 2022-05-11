package com.mak.demo.poll.manager.port.filter;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PollFilter extends BaseFilter{

    private Long id;
    private String question;
    private String username;
    private Boolean isApproved;
    private Boolean isActive;
    private List<Long> choiceIds;
    private LocalDateTime expirationDateTime;

}
