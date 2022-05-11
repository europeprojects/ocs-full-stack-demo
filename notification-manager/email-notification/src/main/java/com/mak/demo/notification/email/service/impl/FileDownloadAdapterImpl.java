package com.mak.demo.notification.email.service.impl;


import com.mak.demo.notification.manager.port.email.adapter.FileDownloadAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileDownloadAdapterImpl implements FileDownloadAdapter {

	private final AsyncHttpClient httpClient;
	private String destinationFolder = "attachments";
	private static final List<String> protocols = List.of("https","http");
	
	@Override
	public String downloadFiles(Map<String, String> fileUrlAndDestFilePathMap) {

		String destinationPath = destinationFolder +"/"+UUID.randomUUID().toString();
		Map<String, String> emailFolderPath = setDestinationFilePath(fileUrlAndDestFilePathMap, destinationPath);
		
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		for (String url : emailFolderPath.keySet()) {
			String destFilePath = emailFolderPath.get(url);
			if (Objects.isNull(destFilePath)) {
				log.warn("Destination file path is empty for source url : {}", url);
			}else{
				try {
					if (protocols.contains(new URL(url).getProtocol())) {
						tasks.add(downloadHttpClient(url, destFilePath));
					} else {
						tasks.add(download(url, destFilePath));
					}
				} catch (MalformedURLException e) {
					log.error("The url : {} is not valid!", e);
				}
			}

		}
		CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()])).join();
		return destinationPath;
	}
	
	private Map<String, String> setDestinationFilePath(Map<String, String> fileUrlAndDestFilePathMap, String baseDestinationPath) {

		Map<String, String> result = new HashMap<>();
		fileUrlAndDestFilePathMap.entrySet().forEach(e->{
			result.put(e.getKey(), baseDestinationPath+"/"+e.getValue());
		});
		return result;
	}

	private CompletableFuture<Void> download(String fileURL, String destFilePath) {

		return CompletableFuture.runAsync(()->{
			try {
				URL url = new URL(fileURL);
				outWithNIO(destFilePath,url.openStream());
			} catch (IOException e) {
				log.error("The file is exist on url : {}, can not be downloaded!", e);
			}
		});
	}
	
	private CompletableFuture<Void> downloadHttpClient(String fileURL, String destFilePath) {

		return httpClient
			.prepareGet(fileURL)
			.execute()
			.toCompletableFuture()
			.thenApply(Response::getResponseBodyAsStream)
			.thenAccept(in ->{
				try {
					outWithNIO(destFilePath,in);
				} catch (IOException e) {
					log.error("The file is exist on url : {}, can not be downloaded!", e);
				}
			});

	}

	private void outWithNIO(String destFilePath, InputStream in) throws IOException{

		File file = new File(destFilePath);
		file.getParentFile().mkdirs();
		try (ReadableByteChannel readableByteChannel = Channels.newChannel(in);
			 FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
			 FileChannel fileChannel = fileOutputStream.getChannel()) {
			 fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		}
	}

}
