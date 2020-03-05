package com.eypery.lts.aio.exceptions;

public class ClienteExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteExistsException(String message) {
		super(message);
	}
}
