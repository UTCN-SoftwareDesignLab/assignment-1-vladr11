package com.utcn.se.vladrusu.services;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.PasswordTooShortException;
import com.utcn.se.vladrusu.exceptions.UsernameAlreadyExistsException;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.EmployeeRole;

public interface IGeneralService {
	
	public EmployeeAccount signUp(String fullName, String username, String password, EmployeeRole role)
			throws UsernameAlreadyExistsException, PasswordTooShortException, MissingFieldsException;

	public boolean logIn(String username, String password);
	
	public boolean isAdministrator(String username);
	
	public boolean isValidUser(String username);

	public EmployeeAccount getEmployeeById(int id);
	public EmployeeAccount getEmployeeByUsername(String username);

}
