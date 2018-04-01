package com.utcn.se.vladrusu.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.utcn.se.vladrusu.exceptions.MissingFieldsException;
import com.utcn.se.vladrusu.exceptions.BillAlreadyPaidException;
import com.utcn.se.vladrusu.exceptions.ClientAlreadyExistsException;
import com.utcn.se.vladrusu.exceptions.IllegalAmountException;
import com.utcn.se.vladrusu.exceptions.AccountNotFoundException;
import com.utcn.se.vladrusu.exceptions.ClientNotFoundException;
import com.utcn.se.vladrusu.exceptions.InvalidCNPException;
import com.utcn.se.vladrusu.model.Account;
import com.utcn.se.vladrusu.model.AccountType;
import com.utcn.se.vladrusu.model.ClientAccount;
import com.utcn.se.vladrusu.model.UtilityBill;
import com.utcn.se.vladrusu.services.IEmployeeService;

@RestController
@RequestMapping("/employee")
public class ClientController {
	
	@Autowired
	IEmployeeService employeeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView displayClientsPage(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			return new ModelAndView("redirect:http://localhost:8080/assignment1/login/");
		}
		return new ModelAndView("employee");
	}
	
	@RequestMapping(value = "/clients", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String clients(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		List<ClientAccount> accounts = employeeService.getAllClients();
		String content = "[";
		for (int i = 0; i < accounts.size(); i++) {
			ClientAccount account = accounts.get(i);
			content += account.toString();
			if (i != accounts.size() - 1) {
				content += ",";
			}
		}
		content += "]";
		
		String json = "{\"count\":" + accounts.size() +
				",\"results\":" + content + "}";
		
		return json;
	}
	
	@RequestMapping(value = "/clients", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String addClient(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String cnp = request.getParameter("cnp");
		int identityCardNumber = Integer.parseInt(request.getParameter("identity_card_number"));
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		
		String employeeName = getSessionUsername(request.getSession());
		
		ClientAccount account = null;
		try {
			account = employeeService.addClientInformation(employeeName,
					firstname,
					lastname,
					cnp,
					identityCardNumber,
					address,
					email);
		} catch (MissingFieldsException e) {
			response.setStatus(400);
			return "{\"error\":\"All fields are mandatory!\"}";
		} catch (ClientAlreadyExistsException e) {
			response.setStatus(400);
			return "{\"error\":\"Client already in the database!\"}";
		} catch (InvalidCNPException e) {
			response.setStatus(400);
			return "{\"error\":\"CNP is invalid!\"}";
		}
		return account.toString();
	}
	
	@RequestMapping(value = "/clients/{client_id}", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String editClient(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int client_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String cnp = request.getParameter("cnp");
		int identityCardNumber = Integer.parseInt(request.getParameter("identity_card_number"));
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String employeeName = getSessionUsername(request.getSession());
		
		try {
			employeeService.editClientInformation(employeeName,
					client_id,
					firstname,
					lastname,
					cnp,
					identityCardNumber,
					address,
					email);
		} catch (MissingFieldsException e) {
			response.setStatus(400);
			return "{\"error\":\"All fields are mandatory!\"}";
		} catch (InvalidCNPException e) {
			response.setStatus(400);
			return "{\"error\":\"CNP is invalid!\"}";
		} catch (ClientNotFoundException e) {
			response.setStatus(400);
			return "{\"error\":\"Client does not exist!\"}";
		}
		
		return "";
	}
	
	// Accounts
	@RequestMapping(value = "/accounts", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String accounts(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		List<Account> accounts = employeeService.getAllBankAccounts();
		String content = "[";
		for (int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			content += account.toString();
			if (i != accounts.size() - 1) {
				content += ",";
			}
		}
		content += "]";
		
		String json = "{\"count\":" + accounts.size() +
				",\"results\":" + content + "}";
		
		return json;
	}
	
	@RequestMapping(value = "clients/{client_id}/accounts", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String accountsForClient(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int client_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		List<Account> accounts = employeeService.getBankAccountsForClient(client_id);
		String content = "[";
		for (int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			content += account.toString();
			if (i != accounts.size() - 1) {
				content += ",";
			}
		}
		content += "]";
		
		String json = "{\"count\":" + accounts.size() +
				",\"results\":" + content + "}";
		
		return json;
	}
	
	@RequestMapping(value = "/clients/{client_id}/accounts", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String addAccount(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int client_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		float balance = Float.parseFloat(request.getParameter("balance"));
		int typeId = Integer.parseInt(request.getParameter("type"));
		java.util.Date utileDate = new java.util.Date();
		Date creationDate = new Date(utileDate.getTime());
		AccountType type;
		if (typeId == 0) {
			type = AccountType.SPENDING;
		} else {
			type = AccountType.SAVING;
		}
		
		String employeeUsername = getSessionUsername(request.getSession());
		
		Account account = null;
		
			try {
				account = employeeService.addBankAccountToDatabase(employeeUsername,
						type,
						balance,
						creationDate);
				employeeService.addBankAccountForClient(employeeUsername, client_id, account);
			} catch (MissingFieldsException e) {
				response.setStatus(400);
				return "{\"error\":\"All fields are mandatory!\"}";
			} catch (AccountNotFoundException e) {
				response.setStatus(400);
				return "{\"error\":\"Client account does not exist!\"}";
			}
		return account.toString();
	}
	
	@RequestMapping(value = "/clients/{client_id}/accounts/{account_id}", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String editAccount(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int client_id,
			@PathVariable int account_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		float balance = Float.parseFloat(request.getParameter("balance"));
		int typeId = Integer.parseInt(request.getParameter("type"));
		java.util.Date utileDate = new java.util.Date();
		Date creationDate = new Date(utileDate.getTime());
		AccountType type;
		if (typeId == 0) {
			type = AccountType.SPENDING;
		} else {
			type = AccountType.SAVING;
		}
		String employeeUsername = getSessionUsername(request.getSession());
		
		try {
			employeeService.editBankAccountForClient(employeeUsername,
					client_id,
					account_id,
					type,
					balance,
					creationDate);
		} catch (AccountNotFoundException e) {
			response.setStatus(400);
			return "{\"error\":\"The account does not exist.\"}";
		}
		
		return "{\"Success\":1}";
	}
	
	@RequestMapping(value = "/clients/{client_id}/accounts/{account_id}", method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteAccount(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int client_id,
			@PathVariable int account_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String employeeUsername = getSessionUsername(request.getSession());
		try {
			employeeService.deleteBankAccountForClient(employeeUsername, client_id, account_id);
		} catch (ClientNotFoundException e) {
			response.setStatus(400);
			return "{\"error\":\"Client does not exist!\"}";
		}
		return "{\"Success\":1}";
	}
	
	@RequestMapping(value = "/clients/{client_id}/accounts/{account_id}/transfer", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String transfer(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int client_id,
			@PathVariable int account_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String employeeUsername = getSessionUsername(request.getSession());
		float sum = Float.parseFloat(request.getParameter("sum"));
		int targetId = Integer.parseInt(request.getParameter("target_account"));
		try {
			employeeService.tranferMoneyBetweenBankAccounts(employeeUsername, account_id, targetId, sum);
		} catch (AccountNotFoundException e) {
			response.setStatus(400);
			return "{\"error\":\"Account does not exist!\"}";
		} catch (IllegalAmountException e) {
			response.setStatus(400);
			return "{\"error\":\"The transaction cannot be processed with this sum!\"}";
		}
		return "{\"success\":1}";
	}
	
	// Utility bills
	
	@RequestMapping(value = "/utility_bills", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String utilityBills(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		List<UtilityBill> utilityBills = employeeService.getAllBills();
		String content = "[";
		for (int i = 0; i < utilityBills.size(); i++) {
			UtilityBill utilityBill = utilityBills.get(i);
			content += utilityBill.toString();
			if (i != utilityBills.size() - 1) {
				content += ",";
			}
		}
		content += "]";
		String json = "{\"count\":" + utilityBills.size() + 
				",\"results\":" + content + "}";
		return json;
	}
	
	@RequestMapping(value = "/utility_bills", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String createBill(HttpServletRequest request, HttpServletResponse response) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		System.out.println(request.getParameter("amount_to_pay") + request.getParameter("firstname"));
		float amount = Float.parseFloat(request.getParameter("amount_to_pay"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String cnp = request.getParameter("cnp");
		String address = request.getParameter("address");
		UtilityBill bill = new UtilityBill(firstname, lastname, cnp, address, amount, false);
		bill = employeeService.addBill(bill);
		return bill.toString();
	}
	
	@RequestMapping(value = "/utility_bills/{bill_id}", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String pay(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int bill_id) {
		String username = getSessionUsername(request.getSession());
		if (!employeeService.hasEmployeePermission(username)) {
			response.setStatus(403);
			return "{\"error\":\"You don't have permission for the requested resource.\"}";
		}
		String employeeUsername = getSessionUsername(request.getSession());
		int accountId = Integer.parseInt(request.getParameter("account_id"));
		UtilityBill utilityBill = employeeService.getUtilityBill(bill_id);
		try {
			employeeService.processUtilitiesBill(employeeUsername, accountId, utilityBill);
		} catch (AccountNotFoundException e) {
			response.setStatus(400);
			return "{\"error\":\"Account does not exist!\"}";
		} catch (IllegalAmountException e) {
			response.setStatus(400);
			return "{\"error\":\"The sum cannot be processed!\"}";
		} catch (BillAlreadyPaidException e) {
			response.setStatus(400);
			return "{\"error\":\"The bill is already paid!\"}";
		}
		return "{\"success\":1}";
	}
	
	@RequestMapping(value = "/utility_bills/{bill_id}", method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteBill(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int bill_id) {
		employeeService.deleteBill(bill_id);
		return "{\"success\":1}";
	}
	
	private String getSessionUsername(HttpSession session) {
		String username = (String)session.getAttribute("token");
		return username;
	}

}
