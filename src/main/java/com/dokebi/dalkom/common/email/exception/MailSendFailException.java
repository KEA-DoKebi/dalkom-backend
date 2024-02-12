package com.dokebi.dalkom.common.email.exception;

import javax.mail.MessagingException;

public class MailSendFailException extends MessagingException {
	public MailSendFailException(String message) {
		super(message);
	}
}