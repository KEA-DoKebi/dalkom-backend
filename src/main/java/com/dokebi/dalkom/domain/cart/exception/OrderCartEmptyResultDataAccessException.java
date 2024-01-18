package com.dokebi.dalkom.domain.cart.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class OrderCartEmptyResultDataAccessException extends EmptyResultDataAccessException {
	public OrderCartEmptyResultDataAccessException(int expectedSize) {

		super(expectedSize);
	}
}
