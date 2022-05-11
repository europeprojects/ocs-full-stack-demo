package com.mak.demo.poll.manager.app.model.email.domain;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Recipients {

	private List<String> toList;
	private List<String> ccList;
	private List<String> bccList;

}
