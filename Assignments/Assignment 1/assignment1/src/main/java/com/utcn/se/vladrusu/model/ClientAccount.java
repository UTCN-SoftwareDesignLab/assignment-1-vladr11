package com.utcn.se.vladrusu.model;

import java.io.Serializable;
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
@Table(name = "client_account")
public class ClientAccount extends ModelEntity implements Serializable {

	private static final long serialVersionUID = -8564549415658411123L;

	@Id
	@Column(name = "client_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int clientId;
	
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;

	@Column(name = "cnp")
	private String cnp;
	
	@Column(name = "identity_card_number")
	private int identityCardNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clientId")
	private List<ClientBankAccount> clientBankAccounts;
	

	public ClientAccount() {
	}

	public ClientAccount(String firstname, String lastname, String cnp, int identityCardNumber, String address,
			String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.cnp = cnp;
		this.identityCardNumber = identityCardNumber;
		this.address = address;
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public int getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(int identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setId(int id) {
		this.clientId = id;
	}

	@Override
	public int getId() {
		return this.clientId;
	}
	
	@Override
	public String toString() {
		return "{\"id\":" + this.getId() +
				",\"firstname\":\"" + this.getFirstname() +
				"\",\"lastname\":\"" + this.getLastname() +
				"\",\"address\":\"" + this.getAddress() +
				"\",\"identity_card_number\":" + this.getIdentityCardNumber() +
				",\"cnp\":\"" + this.getCnp() +
				"\",\"email\":\"" + this.getEmail() + "\"}";
	}

}

