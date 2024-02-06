package com.dokebi.dalkom.domain.chat.exception;

public class GptResponseFailException extends RuntimeException {
	public GptResponseFailException(String message) {
		super(message);
	}
}
