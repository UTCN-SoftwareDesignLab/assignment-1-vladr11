package com.utcn.se.vladrusu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.PasswordTooShortException;
import com.utcn.se.vladrusu.exceptions.UsernameAlreadyExistsException;
import com.utcn.se.vladrusu.model.ActivityLog;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.EmployeeRole;

@Service("administratorService")
@Qualifier("administratorService")
public class AdministratorService extends GeneralService implements IAdministratorService {

	public List<ActivityLog> getActivityLogForEmployee(int id) {
		return dao.getActivityLogByEmployeeId(id);
	}

	public List<EmployeeAccount> getAllEmployees() {
		return dao.getAllEmployees();
	}

	public EmployeeAccount addEmployee(String employeeName, String employeeUserame, String employeePassword,
			EmployeeRole role) throws UsernameAlreadyExistsException, PasswordTooShortException, MissingFieldsException {

		return this.signUp(employeeName, employeeUserame, employeePassword, role);

	}

	public boolean editEmployee(int employeeId, String newEmployeeName, String newEmployeeUsername,
			String newEmployeePassword, EmployeeRole newRole) throws MissingFieldsException {
		
		if (newEmployeeName.isEmpty() || newEmployeePassword.isEmpty() || newEmployeePassword.isEmpty()) {
			throw new MissingFieldsException("");
		}

		EmployeeAccount employee = dao.getEmployeeById(employeeId);
		employee.setFullname(newEmployeeName);
		employee.setUsername(newEmployeeUsername);
		employee.setPassword(newEmployeePassword);
		employee.setRoleId(newRole);
	
		return dao.update(employee);
	}

	public boolean deleteEmployee(int employeeId) {
		
		EmployeeAccount employee = dao.getEmployeeById(employeeId);
		return dao.delete(employee);
	}
	
	@Override
	public boolean hasAdminPermission(String username) {
		return (isValidUser(username) && isAdministrator(username));
	}
}
