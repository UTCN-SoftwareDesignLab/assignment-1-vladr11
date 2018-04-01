package com.utcn.se.vladrusu.exceptions;


public class UsernameAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1250302874856636375L;

	public UsernameAlreadyExistsException(String msg) {
		super(msg);
	}

}
