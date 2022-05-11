package com.mak.demo.poll.manager.app.model.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollDto extends BaseDTO{

    private Long id;
    private String username;
    private String question;
    private List<ChoiceDto> choices;
    private LocalDateTime expirationDateTime;

}
