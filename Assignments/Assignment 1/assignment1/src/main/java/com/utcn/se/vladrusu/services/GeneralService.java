package com.utcn.se.vladrusu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utcn.se.vladrusu.dataAccessLayer.DataAccess;
import com.utcn.se.vladrusu.dataAccessLayer.IDataAccess;
import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.PasswordTooShortException;
import com.utcn.se.vladrusu.exceptions.UsernameAlreadyExistsException;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.EmployeeRole;

@Service("generalService")
@Qualifier("generalService")
public class GeneralService implements IGeneralService {

	@Autowired
	@Qualifier("dao")
	protected IDataAccess dao;

	public void setDao(DataAccess dao) {
		this.dao = dao;
	}

	public IDataAccess getDao() {
		return dao;
	}

	public void setDao(IDataAccess dao) {
		this.dao = dao;
	}

	@Transactional
	public EmployeeAccount signUp(String fullName, String username, String password, EmployeeRole role)
			throws UsernameAlreadyExistsException, PasswordTooShortException, MissingFieldsException {

		if (password == null || password.length() < 4) {
			throw new PasswordTooShortException("Password must have at least 4 characters");
		}
		
		
		List<EmployeeAccount> allEmployees = dao.getAllEmployees();
		if (allEmployees != null) {
			for (EmployeeAccount it : allEmployees) {
				if (it.getUsername().equals(username)) {
					throw new UsernameAlreadyExistsException("The username already exists");
				}
			}
		}

		if ((fullName == null || fullName.equals("")) || (role == null))
			throw new MissingFieldsException("All fields are mandatory");

		EmployeeAccount newAccount = new EmployeeAccount(fullName, username, password, role);
		EmployeeAccount savedAccount = (EmployeeAccount) dao.save(newAccount);
		return savedAccount;

	}

	@Transactional
	public boolean logIn(String username, String password) {

		List<EmployeeAccount> allEmployees = dao.getAllEmployees();
		for (EmployeeAccount it : allEmployees) {
			if (it.getUsername().equals(username)) {
				{
					if (it.getPassword().equals(password)) {
						dao.update(it);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Transactional
	public boolean isAdministrator(String username) {
		EmployeeAccount account = getEmployeeByUsername(username);
		if (account == null) {
			return false;
		}
		return (account.getRoleId().equals(EmployeeRole.ADMINISTRATOR));
	}
	
	@Transactional
	public boolean isValidUser(String username) {
		EmployeeAccount account = getEmployeeByUsername(username);
		return account != null;
	}
	
	public EmployeeAccount getEmployeeById(int id) {
		return dao.getEmployeeById(id);
	}
	
	public EmployeeAccount getEmployeeByUsername(String username) {
		return dao.getEmployeeByUsername(username);
	}

}
