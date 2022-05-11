package com.mak.demo.poll.manager.port.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Vote extends BaseDomain {

    private Poll poll;
    private Choice choice;
    private String username;

}
