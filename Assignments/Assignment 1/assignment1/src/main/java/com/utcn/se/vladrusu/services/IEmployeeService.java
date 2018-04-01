package com.utcn.se.vladrusu.services;

import java.sql.Date;
import java.util.List;

import com.utcn.se.vladrusu.exceptions.*;
import com.utcn.se.vladrusu.model.*;

public interface IEmployeeService {

	public ClientAccount getClientInformation(int id) throws ClientNotFoundException;
	
	public ClientAccount getClientByCNP(String cnp) throws ClientNotFoundException;

	public List<ClientAccount> getAllClients();

	public ClientAccount addClientInformation(String employeeName, String firstname, String lastname, String cnp,
			int identityCardNumber, String address, String email)
			throws MissingFieldsException, ClientAlreadyExistsException, InvalidCNPException;

	public boolean editClientInformation(String employeeName, int id, String newFirstname, String newLastname,
			String newCnp, int newIdentityCardNumber, String newAddress, String newEmail)
			throws MissingFieldsException, InvalidCNPException, ClientNotFoundException;

	public List<Account> getAllBankAccounts();

	public Account addBankAccountToDatabase(String employeeUsername, AccountType type, float amountOfMoney, Date creationDate) throws MissingFieldsException;
	
	public List<Account> getBankAccountsForClient(int id);

	public ClientBankAccount addBankAccountForClient(String employeeName, int id, Account bankAccount) throws AccountNotFoundException;

	public boolean editBankAccountForClient(String employeeName, int id, int accountId, AccountType type,
			float amountOfMoney, Date creationDate) throws AccountNotFoundException;

	public boolean deleteBankAccountForClient(String employeeName, int id, int accountId) throws ClientNotFoundException;

	public boolean tranferMoneyBetweenBankAccounts(String employeeName, int fromAccountId, int toAccountId,
			float amountOfMoney) throws AccountNotFoundException, IllegalAmountException;

	public UtilityBill getUtilityBill(int billId);
	
	public List<UtilityBill> getAllBills();
	
	public boolean processUtilitiesBill(String employeeName, int accountId, UtilityBill utilityBill)
			throws AccountNotFoundException, IllegalAmountException, BillAlreadyPaidException;

	public UtilityBill addBill(UtilityBill ub);
	
	public boolean deleteBill(int billId);
	
	public boolean hasEmployeePermission(String username);

}
