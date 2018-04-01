package com.utcn.se.vladrusu.dataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	private Connection con;
	private static DatabaseConnection instance = new DatabaseConnection();

	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment1?useSSL=false",
					USERNAME,
					PASSWORD);
			System.out.println("Connection established!");
		} catch (Exception ex) {
			System.out.println("Database connection error: " + ex);
		}
	}

	public static DatabaseConnection getInstance() {
		return instance;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public void closeConnection() {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}