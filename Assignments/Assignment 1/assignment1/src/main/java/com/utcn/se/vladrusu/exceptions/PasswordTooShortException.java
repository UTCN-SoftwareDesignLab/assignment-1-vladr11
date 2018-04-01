package com.utcn.se.vladrusu.exceptions;

public class PasswordTooShortException extends Exception {

	private static final long serialVersionUID = 4584665896361964925L;
	
	public PasswordTooShortException(String msg)
	{
		super(msg);
	}

}
