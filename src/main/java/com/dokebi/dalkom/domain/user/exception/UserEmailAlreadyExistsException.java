package com.dokebi.dalkom.domain.user.exception;

public class UserEmailAlreadyExistsException extends RuntimeException {
	public UserEmailAlreadyExistsException(String message) {
		super(message);
	}
}
