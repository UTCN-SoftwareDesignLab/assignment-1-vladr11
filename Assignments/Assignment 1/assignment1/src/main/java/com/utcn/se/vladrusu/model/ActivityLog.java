package com.utcn.se.vladrusu.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activity_log")
public class ActivityLog extends ModelEntity implements Serializable {

	private static final long serialVersionUID = 5749154540415224817L;

	@Id
	@Column(name = "log_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int logId;
	
	@Column(name = "employee_id")
	private int employeeId;
	
	@Column(name = "description")
	private String description;
	
	public ActivityLog() {}
	
	public ActivityLog(int employeeId, String description)
	{
		this.employeeId = employeeId;
		this.description = description;
	}
	
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setId(int id) {
		this.logId = id;

	}

	@Override
	public int getId() {
		return this.logId;
	}
	
	@Override
	public String toString() {
		return "{\"id\":" + this.getId() +
				",\"description\":\"" + this.getDescription() +
				"\",\"employee_id\":" + this.getEmployeeId() + "}";
	}

}
