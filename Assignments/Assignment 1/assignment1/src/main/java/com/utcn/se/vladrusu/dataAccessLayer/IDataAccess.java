package com.utcn.se.vladrusu.dataAccessLayer;

import java.util.List;

import com.utcn.se.vladrusu.model.ActivityLog;
import com.utcn.se.vladrusu.model.Account;
import com.utcn.se.vladrusu.model.ClientAccount;
import com.utcn.se.vladrusu.model.ClientBankAccount;
import com.utcn.se.vladrusu.model.EmployeeAccount;
import com.utcn.se.vladrusu.model.ModelEntity;
import com.utcn.se.vladrusu.model.UtilityBill;

public interface IDataAccess {

	public ModelEntity save(ModelEntity modelEntity);

	public boolean update(ModelEntity modelEntity);
	
	public boolean delete(ModelEntity modelEntity);

	public List<ClientAccount> getAllClients();
	
	public List<EmployeeAccount> getAllEmployees();
	
	public List<Account> getAllBankAccounts();
	
	public List<Account> getBankAccountsByClientId(int id);

	public EmployeeAccount getEmployeeById(int id);
	
	public EmployeeAccount getEmployeeByUsername(String username);

	public ClientAccount getClientById(int id);
	
	public ClientAccount getClientByCNP(String cnp);
	
	public ClientBankAccount getClientBankAccount(int clientId, int accountId);
	
	public List<ActivityLog> getActivityLogByEmployeeId(int id);

	public UtilityBill getUtilityBill(int billId);
	
	public List<UtilityBill> getAllBills();
	
}
