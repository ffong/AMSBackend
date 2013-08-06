package com.ubc.ca.service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ubc.ca.exception.InvalidLoginException;

public class LoginService {

	public String authenticate(String userName, String password) throws ConnectException, InvalidLoginException
	{
		Connection con=ConnectionService.getConnection();

		try {
			String query="select * from Login where username=?";
			PreparedStatement preparestatement=con.prepareStatement(query);
			preparestatement.setString(1, userName);
			ResultSet rs=preparestatement.executeQuery();
			if(null!=rs)
			{
				rs.next();
				if(rs.getString(2).equals(password))
				return rs.getString(3);
				else 
					throw new InvalidLoginException("Invalid password");
			}
		} catch (SQLException e) {
			throw new ConnectException(e.getMessage());
		}
		finally
		{
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		return "failure";
		
		
	}
}
