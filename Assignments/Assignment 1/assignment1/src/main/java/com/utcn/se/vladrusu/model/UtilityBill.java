package com.utcn.se.vladrusu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utility_bill")
public class UtilityBill extends ModelEntity implements Serializable {

	private static final long serialVersionUID = -2714454923256940246L;

	@Id
	@Column(name = "bill_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int billId;
	
	@Column(name = "client_firstname")
	private String clientFirstname;
	
	@Column(name = "client_lastname")
	private String clientLastname;
	
	@Column(name = "client_cnp")
	private String clientCNP;
	
	@Column(name = "client_address")
	private String clientAddress;
	
	@Column(name = "amount_to_pay")
	private float amountToPay;
	
	@Column(name = "paid")
	private boolean paid;
	
	public UtilityBill() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public UtilityBill(String clientFirstname, String clientLastname, String clientCNP,
			String clientAddress, float amountToPay, boolean paid) {
		super();
		this.clientFirstname = clientFirstname;
		this.clientLastname = clientLastname;
		this.clientCNP = clientCNP;
		this.clientAddress = clientAddress;
		this.amountToPay = amountToPay;
		this.paid = paid;
	}

	public String getClientFirstname() {
		return clientFirstname;
	}

	public void setClientFirstname(String clientFirstname) {
		this.clientFirstname = clientFirstname;
	}

	public String getClientLastname() {
		return clientLastname;
	}

	public void setClientLastname(String clientLastname) {
		this.clientLastname = clientLastname;
	}

	public String getClientCNP() {
		return clientCNP;
	}

	public void setClientCNP(String clientCNP) {
		this.clientCNP = clientCNP;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public float getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(float amountToPay) {
		this.amountToPay = amountToPay;
	}
	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public void setId(int id) {
		this.billId = id;
	}

	@Override
	public int getId() {
		return this.billId;
	}
	
	@Override
	public String toString() {
		return "{\"id\":" + this.getId() +
				",\"firstname\":\"" + this.getClientFirstname() +
				"\",\"lastname\":\"" + this.getClientLastname() +
				"\",\"cnp\":\"" + this.getClientCNP() +
				"\",\"address\":\"" + this.getClientAddress() +
				"\",\"amount_to_pay\":" + this.getAmountToPay() +
				",\"paid\":" + this.isPaid() + "}";
	}

}
