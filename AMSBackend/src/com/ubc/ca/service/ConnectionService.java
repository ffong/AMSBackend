package com.ubc.ca.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
	
	private static Connection con = null;
	private final static String URL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
	private final static String USER = "";
	private final static String PASSWORD = "";
	
	public ConnectionService() {

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con = getConnection();
		// Class.forName("oracle.jdbc.driver.OracleDriver");
		
	}
	
	// only creates one connection
	public static Connection getConnection() {
		try {
			if (con == null) {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("Connected to database.");
			}
		} catch (SQLException e) {
			System.out.println("Problem with connecting to the database.");
			e.printStackTrace();
		}
		return con;
	}
}
