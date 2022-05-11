package com.mak.demo.notification.email.service.impl;


import com.mak.demo.notification.email.service.validator.EmailValidator;
import com.mak.demo.notification.manager.port.email.adapter.EmailServiceAdapter;
import com.mak.demo.notification.manager.port.email.adapter.FileDownloadAdapter;
import com.mak.demo.notification.manager.port.email.domain.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailServiceAdapterImpl implements EmailServiceAdapter, EmailValidator {

	private final FileDownloadAdapter downloadAdapter;
	private final JavaMailSender emailSender;
	
	@Override
	public void sendEmail(EmailRequest request) throws MessagingException {
		 validateMail(request);
		 if (request.isMailBodyHTML()) {
			sendHTMLMail(request);
		}else {
			sendEmailWithAttachments(request);
		}
	}

	@Override
	public void validateMail(EmailRequest request) throws MessagingException {
		if(Objects.isNull(request.getFrom()) || Objects.isNull(request.getRecipients()) ||
				request.getRecipients().getToList().isEmpty() &&
						request.getRecipients().getCcList().isEmpty()
						  && request.getRecipients().getBccList().isEmpty() ){
			throw new MessagingException("from and recipients are mandatory fields");
		}
	}

	public void sendEmailWithAttachments(EmailRequest request) throws MessagingException {

		MimeMessageHelper messageHelper = createMimeMessage(request);
		messageHelper.setText(request.getBody(), false); // plain-text
		send( messageHelper,request);
	}
	
	public void sendHTMLMail(EmailRequest request) throws MessagingException {

		MimeMessageHelper messageHelper = createMimeMessage(request);
		messageHelper.setText(request.getBody(), true);
		send( messageHelper,request);
	}


	private MimeMessageHelper createMimeMessage(EmailRequest request) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setSubject(request.getSubject());
		helper.setFrom(request.getFrom());
		helper.setTo(request.getRecipients().getToList().toArray(new String[request.getRecipients().getToList().size()]));
		helper.setCc(request.getRecipients().getCcList().toArray(new String[request.getRecipients().getCcList().size()]));
		helper.setBcc(request.getRecipients().getBccList().toArray(new String[request.getRecipients().getBccList().size()]));

		return helper;
	}

	private void send(MimeMessageHelper helper, EmailRequest request) throws MessagingException {

		if (CollectionUtils.isEmpty(request.getUrlAttachmentsMap())) {
			emailSender.send(helper.getMimeMessage());
			return;
		};

		sendForUrlAttachments(helper, request);
	}

	private void sendForUrlAttachments(MimeMessageHelper helper, EmailRequest request) throws MessagingException {
		File folder = new File(downloadAdapter.downloadFiles(request.getUrlAttachmentsMap()));
		Stream<File> files = Arrays.stream(folder.listFiles());
		try {
			addAttachments(helper, request,files);
			emailSender.send(helper.getMimeMessage());
		} finally {
			if (folder != null) {
				try {
					FileUtils.deleteDirectory(folder);
				} catch (IOException e) {
					log.error("deleting attachment folder exc ", e);
				}
			}
		}
	}


	private void addAttachments(MimeMessageHelper helper, EmailRequest request,Stream<File> listOfFiles) throws MessagingException {

		listOfFiles.forEach(file->{
			String fileName = file.getName();
			if (request.getInlineAttachments().contains(fileName)) {
				try {
					helper.addInline(fileName, file);
				} catch (MessagingException e) {
					log.error("addInline exc ", e);
				}
			}else{
				try {
					helper.addAttachment(fileName,  new FileSystemResource(file));
				} catch (MessagingException e) {
					log.error("addAttachment exc ", e);
				}
			}
		});
	}


}
