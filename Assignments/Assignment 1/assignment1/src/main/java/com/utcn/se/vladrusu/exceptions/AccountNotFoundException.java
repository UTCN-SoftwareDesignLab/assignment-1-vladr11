package com.utcn.se.vladrusu.exceptions;

public class AccountNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4192579812817061930L;

	public AccountNotFoundException(String msg)
	{
		super(msg);
	}
}
