package com.utcn.se.vladrusu.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "employee_account")
public class EmployeeAccount extends ModelEntity implements Serializable {

	private static final long serialVersionUID = -3515426091251869420L;

	@Id
	@Column(name = "employee_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeeId;
	
	@Column(name = "fullname")
	private String fullname;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private EmployeeRole roleId;
	
	@Transient
	private String newPassword;
	
	public EmployeeAccount() {
	}

	public EmployeeAccount(String fullname, String username, String password, EmployeeRole roleId) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmployeeRole getRoleId() {
		return roleId;
	}

	public void setRoleId(EmployeeRole roleId) {
		this.roleId = roleId;
	}

	@Override
	public void setId(int id) {
		this.employeeId = id;
	}

	@Override
	public int getId() {
		return this.employeeId;
	}
	
	@Override
	public String toString() {
		return "{\"id\":" + this.getId() +
				",\"fullname\":\"" + this.getFullname() +
				"\",\"username\":\"" + this.getUsername() +
				"\",\"password\":\"" + this.getPassword() +
				"\",\"role\":" + this.getRoleId().ordinal() + "}";
	}

}

