package com.utcn.se.vladrusu.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "client_bank_account")
public class ClientBankAccount extends ModelEntity implements Serializable {

	private static final long serialVersionUID = 8217860769799037651L;

	@Id
	@Column(name = "client_bank_account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int clientBankAccountId;

	@Column(name = "client_id")
	private int clientId;

	@Column(name = "account_id")
	private int accountId;
	
	 public ClientBankAccount() {
	}

	public ClientBankAccount(int clientId, int accountId){
		this.clientId = clientId;
		this.accountId = accountId;
	}

	@Override
	public void setId(int id) {
		this.clientBankAccountId = id;
	}

	@Override
	public int getId() {
		return this.clientBankAccountId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

}
