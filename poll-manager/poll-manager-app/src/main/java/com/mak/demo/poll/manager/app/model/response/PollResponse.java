package com.mak.demo.poll.manager.app.model.response;


import com.mak.demo.poll.manager.app.model.dto.PollDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollResponse {

    private Boolean success;
    private PollDto pollDto;

}
