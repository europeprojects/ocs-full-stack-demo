package com.mak.demo.notification.manager.port.email.adapter;

import java.util.Map;

public interface FileDownloadAdapter {
	
	String downloadFiles(Map<String, String> fileUrlAndDestFilePathMap);
}
