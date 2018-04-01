package com.utcn.se.vladrusu.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends ModelEntity implements Serializable {

	private static final long serialVersionUID = 3558242991375683454L;

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;
	
	@Column(name = "type")
	private AccountType type;
	
	@Column(name = "balance")
	private float balance;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "accountId")
	private List<ClientBankAccount> clientBankAccounts;
	
	
	public Account() {

	}

	public Account(AccountType type, float balance, Date creationDate) {
		super();
		this.type = type;
		this.balance = balance;
		this.creationDate = creationDate;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setId(int id) {
		this.accountId = id;

	}

	@Override
	public int getId() {
		return this.accountId;
	}
	
	public int getOwner() {
		return clientBankAccounts.get(0).getClientId();
	}
	
	@Override
	public String toString() {
		return "{\"id\":" + this.getId() +
				",\"type\":" + this.getType().ordinal() +
				",\"creation_date\":\"" + this.getCreationDate().toString() +
				"\",\"balance\":" + this.getBalance() +
				",\"owner\":" + getOwner() + "}";
	}

}
