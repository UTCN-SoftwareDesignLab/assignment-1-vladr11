package com.utcn.se.vladrusu.exceptions;

public class ClientAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -17263793369001506L;
	
	public ClientAlreadyExistsException(String msg)
	{
		super(msg);
	}

}
