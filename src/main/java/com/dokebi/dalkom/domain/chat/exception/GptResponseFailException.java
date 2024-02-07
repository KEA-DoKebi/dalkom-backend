package com.dokebi.dalkom.domain.chat.exception;

import lombok.Generated;

public class GptResponseFailException extends RuntimeException {
	public GptResponseFailException(String message) {
		super(message);
	}
}
