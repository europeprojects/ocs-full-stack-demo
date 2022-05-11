package com.mak.demo.notification.manager.port.email.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class EmailRequest {

	private String from;
	private Recipients recipients;
	private String subject;
	private String body;
	private List<String> inlineAttachments = new ArrayList<>();
	private Map<String, String> urlAttachmentsMap = new HashMap<>();
	private boolean isMailBodyHTML;

}
