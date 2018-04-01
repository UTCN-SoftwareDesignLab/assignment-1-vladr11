package com.utcn.se.vladrusu.services;

import java.util.List;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.PasswordTooShortException;
import com.utcn.se.vladrusu.exceptions.UsernameAlreadyExistsException;
import com.utcn.se.vladrusu.model.ActivityLog;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.EmployeeRole;

public interface IAdministratorService extends IGeneralService {

	public List<ActivityLog> getActivityLogForEmployee(int id);
	
	public List<EmployeeAccount> getAllEmployees();
		
	public EmployeeAccount addEmployee(String employeeName, String employeeUserame, String employeePassword, EmployeeRole role) throws UsernameAlreadyExistsException, PasswordTooShortException, MissingFieldsException;
	
	public boolean editEmployee(int employeeID, String newEmployeeName, String newEmployeeUsername, String newEmployeePassword, EmployeeRole newRole) throws MissingFieldsException;
	
	public boolean deleteEmployee(int employeeId);
	
	public boolean hasAdminPermission(String username);
	
}
