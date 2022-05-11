package com.mak.demo.poll.manager.app.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceDto extends BaseDTO {
    private Long id;
    private String text;
}
