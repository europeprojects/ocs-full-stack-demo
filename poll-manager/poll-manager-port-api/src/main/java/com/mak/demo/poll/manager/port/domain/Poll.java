package com.mak.demo.poll.manager.port.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poll extends BaseDomain {

    private String question;
    private String username;
    private String email;
    private Boolean isApproved = true;
    private Boolean isActive = true;
    private List<Choice> choices;
    private LocalDateTime expirationDateTime;

}
