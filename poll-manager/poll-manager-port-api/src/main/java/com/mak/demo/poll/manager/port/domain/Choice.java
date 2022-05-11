package com.mak.demo.poll.manager.port.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice extends BaseDomain{

    private String text;

}
