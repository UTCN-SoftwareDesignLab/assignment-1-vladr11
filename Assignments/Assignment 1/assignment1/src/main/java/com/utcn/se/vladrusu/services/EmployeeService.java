package com.utcn.se.vladrusu.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.BillAlreadyPaidException;
import com.utcn.se.vladrusu.exceptions.ClientAlreadyExistsException;
import com.utcn.se.vladrusu.exceptions.IllegalAmountException;
import com.utcn.se.vladrusu.exceptions.AccountNotFoundException;
import com.utcn.se.vladrusu.exceptions.ClientNotFoundException;
import com.utcn.se.vladrusu.exceptions.InvalidCNPException;
import com.utcn.se.vladrusu.model.AccountType;
import com.utcn.se.vladrusu.model.ActivityLog;
import com.utcn.se.vladrusu.model.Account;
import com.utcn.se.vladrusu.model.ClientAccount;
import com.utcn.se.vladrusu.model.ClientBankAccount;
import com.utcn.se.vladrusu.model.UtilityBill;

@Service("employeeService")
@Qualifier("employeeService")
public class EmployeeService extends GeneralService implements IEmployeeService {

	// CRU ON CLIENTS

	public ClientAccount getClientInformation(int id) throws ClientNotFoundException {

		ClientAccount account = dao.getClientById(id);
		if (account != null)
			return account;
		else
			throw new ClientNotFoundException("");

	}

	public ClientAccount addClientInformation(String employeeUsername, String firstname, String lastname, String cnp,
			int identityCardNumber, String address, String email)
			throws MissingFieldsException, ClientAlreadyExistsException, InvalidCNPException {

		if ((firstname == null || firstname.equals("")) || (lastname == null || lastname.equals(""))
				|| (cnp == null || cnp.equals("")))
			throw new MissingFieldsException("");

		if (cnp.length() != 13) {
			throw new InvalidCNPException("");
		}

		List<ClientAccount> allClients = dao.getAllClients();
		if (allClients != null) {
			for (ClientAccount it : allClients) {
				if (it.getFirstname().equals(firstname) && it.getLastname().equals(lastname)
						&& it.getCnp().equals(cnp)) {
					throw new ClientAlreadyExistsException("");
				}
			}
		}

		ClientAccount newAccount = new ClientAccount(firstname, lastname, cnp, identityCardNumber, address, email);
		ClientAccount savedAccount = (ClientAccount) dao.save(newAccount);

		int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
		java.util.Date date = new java.util.Date();
		ActivityLog al = new ActivityLog(employeeId, date.toString() + ": Added client " + firstname + " " + lastname);
		dao.save(al);

		return savedAccount;

	}

	public boolean editClientInformation(String employeeUsername, int id, String newFirstname, String newLastname,
			String newCnp, int newIdentityCardNumber, String newAddress, String newEmail)
			throws MissingFieldsException, InvalidCNPException, ClientNotFoundException {

		if (newFirstname.isEmpty() || newLastname.isEmpty() || newCnp.isEmpty() || newIdentityCardNumber == 0
				|| newAddress.isEmpty() || newEmail.isEmpty()) {
			throw new MissingFieldsException("");
		}

		if (newCnp.length() != 13) {
			throw new InvalidCNPException("");
		}

		ClientAccount client = dao.getClientById(id);
		if (client == null) {
			throw new ClientNotFoundException("");
		}
		client.setFirstname(newFirstname);
		client.setLastname(newLastname);
		client.setCnp(newCnp);
		client.setIdentityCardNumber(newIdentityCardNumber);
		client.setAddress(newAddress);
		client.setEmail(newEmail);

		int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
		java.util.Date date = new java.util.Date();
		ActivityLog al = new ActivityLog(employeeId, date.toString() + ": Edited client with ID: " + id);
		dao.save(al);

		return dao.update(client);
	}

	// CRUD ON BANK ACCOUNTS

	public List<Account> getBankAccountsForClient(int id) {
		return dao.getBankAccountsByClientId(id);
	}

	public ClientBankAccount addBankAccountForClient(String employeeUsername, int id, Account bankAccount)
			throws AccountNotFoundException {

		ClientAccount ca = dao.getClientById(id);
		if (ca == null) {
			throw new AccountNotFoundException("");
		}
		int clientId = ca.getId();
		int accountId = bankAccount.getId();

		ClientBankAccount newAccount = new ClientBankAccount(clientId, accountId);
		ClientBankAccount savedAccount = (ClientBankAccount) dao.save(newAccount);

		int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
		java.util.Date date = new java.util.Date();
		ActivityLog al = new ActivityLog(employeeId, date.toString() + ": Added bank account " + bankAccount.getId()
				+ " for client " + ca.getFirstname() + " " + ca.getLastname());
		dao.save(al);

		return savedAccount;

	}

	public boolean editBankAccountForClient(String employeeUsername, int id, int accountId, AccountType type,
			float amountOfMoney, Date creationDate) throws AccountNotFoundException {

		List<Account> baList = this.getBankAccountsForClient(id);

		if (baList != null) {
			for (Account ba : baList) {
				if (ba.getId() == accountId) {
					ba.setType(type);
					ba.setBalance(amountOfMoney);
					ba.setCreationDate(creationDate);

					int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
					java.util.Date date = new java.util.Date();
					ActivityLog al = new ActivityLog(employeeId, date.toString() + ": Edited bank account " + ba.getId());
					dao.save(al);

					return dao.update(ba);
				}
			}
		} else {
			throw new AccountNotFoundException("");
		}
		return false;
	}

	public boolean deleteBankAccountForClient(String employeeUsername, int id, int accountId)
			throws ClientNotFoundException {

		ClientAccount ca = dao.getClientById(id);
		if (ca == null) {
			throw new ClientNotFoundException("");
		}

		int clientId = ca.getId();
		ClientBankAccount cba = dao.getClientBankAccount(clientId, accountId);

		int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
		java.util.Date date = new java.util.Date();
		ActivityLog al = new ActivityLog(employeeId,
				date.toString() + ": Deleted bank account " + cba.getAccountId() + " of client " + cba.getClientId());
		dao.save(al);

		return dao.delete(cba);
	}

	public List<Account> getAllBankAccounts() {
		return dao.getAllBankAccounts();
	}

	// MONEY TRANSFER

	public boolean tranferMoneyBetweenBankAccounts(String employeeUsername, int fromAccountId, int toAccountId,
			float amountOfMoney) throws AccountNotFoundException, IllegalAmountException {

		List<Account> bas = dao.getAllBankAccounts();
		Account from = null;
		Account to = null;
		if (bas != null) {
			for (Account ba : bas) {
				if (ba.getId() == fromAccountId)
					from = ba;
				else {
					if (ba.getId() == toAccountId) {
						to = ba;
					}
				}
			}
		}

		if (from == null || to == null) {
			throw new AccountNotFoundException("");
		}

		if (amountOfMoney > 0 && (from.getBalance() - amountOfMoney >= 0)) {
			from.setBalance(from.getBalance() - amountOfMoney);
			to.setBalance(to.getBalance() + amountOfMoney);
			boolean toBool = dao.update(to);
			boolean fromBool = dao.update(from);

			if (toBool && fromBool == true) {
				int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
				java.util.Date date = new java.util.Date();
				ActivityLog al = new ActivityLog(employeeId, date.toString() + ": Transfered " + amountOfMoney
						+ "$ from account " + fromAccountId + " to account " + toAccountId);
				dao.save(al);
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalAmountException("");
		}

	}

	public boolean processUtilitiesBill(String employeeUsername, int accountId, UtilityBill utilityBill)
			throws AccountNotFoundException, IllegalAmountException, BillAlreadyPaidException {

		List<Account> baList = dao.getAllBankAccounts();
		Account bankAcc = null;

		if (utilityBill.isPaid()) {
			throw new BillAlreadyPaidException("");
		}

		if (baList != null) {
			for (Account ba : baList) {
				if (ba.getId() == accountId)
					bankAcc = ba;
			}
		}

		if (bankAcc == null) {
			throw new AccountNotFoundException("");
		}

		if (bankAcc.getBalance() - utilityBill.getAmountToPay() < 0) {
			throw new IllegalAmountException("");
		}

		bankAcc.setBalance(bankAcc.getBalance() - utilityBill.getAmountToPay());
		utilityBill.setPaid(true);

		boolean accBool = dao.update(bankAcc);
		boolean utilBool = dao.update(utilityBill);

		if (accBool && utilBool == true) {
			int employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
			java.util.Date date = new java.util.Date();
			ActivityLog al = new ActivityLog(employeeId, date.toString() + ": Accepted " + " payment ( " + utilityBill.getAmountToPay() + "$) from account " + accountId);
			dao.save(al);
			return true;
		} else {
			return false;
		}

	}

	public List<ClientAccount> getAllClients() {
		return dao.getAllClients();
	}

	public Account addBankAccountToDatabase(String employeeUsername, AccountType type, float amountOfMoney,
			Date creationDate) throws MissingFieldsException {

		if ((type == null || amountOfMoney == 0 || creationDate == null))
			throw new MissingFieldsException("");

		Account newAccount = new Account(type, amountOfMoney, creationDate);
		Account savedAccount = (Account) dao.save(newAccount);

		int employeeId = -1;
		try {
			employeeId = dao.getEmployeeByUsername(employeeUsername).getId();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		java.util.Date date = new java.util.Date();
		ActivityLog al = new ActivityLog(employeeId,
				date.toString() + ": Added bank account " + savedAccount.getId() + " to the database");
		dao.save(al);

		return savedAccount;
	}

	public UtilityBill getUtilityBill(int billId) {
		return dao.getUtilityBill(billId);
	}

	public List<UtilityBill> getAllBills() {
		return dao.getAllBills();
	}

	public UtilityBill addBill(UtilityBill ub) {
		return (UtilityBill) dao.save(ub);
	}

	public ClientAccount getClientByCNP(String cnp) throws ClientNotFoundException {
		ClientAccount account = dao.getClientByCNP(cnp);
		if (account != null) {
			return account;
		}
		else {
			throw new ClientNotFoundException("");
		}
	}
	
	@Override
	public boolean deleteBill(int billId) {
		UtilityBill bill = dao.getUtilityBill(billId);
		return dao.delete(bill);
	}
	
	@Override
	public boolean hasEmployeePermission(String username) {
		return (isValidUser(username) && !isAdministrator(username));
	}
}
