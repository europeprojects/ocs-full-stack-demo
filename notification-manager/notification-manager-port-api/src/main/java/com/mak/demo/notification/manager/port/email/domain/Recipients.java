package com.mak.demo.notification.manager.port.email.domain;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Recipients {

	private List<String> toList;
	private List<String> ccList;
	private List<String> bccList;

	public List<String> getToList() {
		return CollectionUtils.isEmpty(toList) ? Collections.emptyList() : toList;
	}

	public List<String> getCcList() {
		return CollectionUtils.isEmpty(ccList) ? Collections.emptyList() : ccList;
	}

	public List<String> getBccList() {
		return CollectionUtils.isEmpty(bccList) ? Collections.emptyList() : bccList;
	}
}
