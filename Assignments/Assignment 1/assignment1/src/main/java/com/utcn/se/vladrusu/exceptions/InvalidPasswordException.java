package com.utcn.se.vladrusu.exceptions;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = -2887894342365230047L;
	
	public InvalidPasswordException(String msg)
	{
		super(msg);
	}

}
