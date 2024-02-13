package com.dokebi.dalkom.common.email.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.dokebi.dalkom.common.email.dto.EmailMessage;
import com.dokebi.dalkom.common.email.exception.MailSendFailException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	public final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public void sendMailMileage(EmailMessage emailMessage, String state, LocalDateTime applyDate, Integer amount) throws
		Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		Context context = new Context();
		context.setVariable("state", state);
		context.setVariable("applyDate", applyDate.format(formatter));
		context.setVariable("amount", String.format("%,d", amount));

		String htmlContent = templateEngine.process("mailSendMileage", context);

		mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
		mimeMessageHelper.setSubject(emailMessage.getTitle()); // 메일 제목
		mimeMessageHelper.setText(htmlContent, true); // 메일 본문 내용, HTML 여부

		try {
			javaMailSender.send(mimeMessage);
			log.info("Success");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void sendMailInquiry(EmailMessage emailMessage, LocalDateTime createdDate) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		Context context = new Context();
		context.setVariable("createdDate", createdDate.format(formatter));

		String htmlContent = templateEngine.process("mailSendInquiry", context);

		mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
		mimeMessageHelper.setSubject(emailMessage.getTitle()); // 메일 제목
		mimeMessageHelper.setText(htmlContent, true); // 메일 본문 내용, HTML 여부

		try {
			javaMailSender.send(mimeMessage);
			log.info("Success");
		} catch (Exception e) {
			throw new MailSendFailException(e.getMessage());
		}
	}
}
