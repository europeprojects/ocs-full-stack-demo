package com.mak.demo.poll.manager.app.auth;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollUser {

    private Long id;
    private String username;
    private String email;

}
