package com.ubc.ca.service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerService {
	private static Connection con;

	public CustomerService() throws ConnectException {
		con = ConnectionService.getConnection();
	}

	public void registerCustomer(String cid, String cname, String cadd, String password, String cphone){
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?)");
			
			ps.setString(1, cid);
			
			// if nothing is passed in, store as empty string (?) 
			ps.setString(2, cname);
			ps.setString(3, cadd);
			ps.setString(4, password);
			ps.setString(5, cphone);
			
			ps.executeUpdate();
			
			con.commit();
			
			System.out.println("Success!"); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQLException");
			e.printStackTrace();
		}
		
				
	}
}
