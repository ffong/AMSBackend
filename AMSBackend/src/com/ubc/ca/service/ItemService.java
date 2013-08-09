package com.ubc.ca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ItemService {

	
	public void UpdateItem(String upc, int quan) throws Exception {

		Connection con = ConnectionService.getConnection();
		int dbstock = 0;

		try {
			// get stock from database
			String query = "SELECT item_stock FROM item WHERE upc = ?";
			PreparedStatement ps = con.prepareStatement(query);

			ps.setString(1, upc);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				System.out.println("UPC not in database");
				return;
			} else {
				dbstock = rs.getInt("item_stock");
			}

		} catch (SQLException e) {
			try {
				con.rollback();
				e.printStackTrace();
				throw new Exception("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new Exception("Error with retrieving stock from database");
		}

		try {
			int current_stock = dbstock + quan;

			// update stock
			PreparedStatement ps1 = con
					.prepareStatement("UPDATE item SET item_stock = ?"
							+ "WHERE upc = ?");

			ps1.setInt(1, current_stock);
			ps1.setString(2, upc);
			ps1.executeQuery();

			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			throw new Exception("Error with updating item quantity");
		}
	}
	
	public void UpdateItem (String upc, int quan, float price) throws Exception {
		
		Connection con = ConnectionService.getConnection();
		
		int dbstock = 0;
		
		if (price < 0) System.out.println("invalid price");
		
		try {
			// get stock from database
			String query = "SELECT item_stock FROM item WHERE upc = ?";
			PreparedStatement ps = con.prepareStatement(query);

			ps.setString(1, upc);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				System.out.println("UPC not in database");
				return;
			} else {
					dbstock = rs.getInt("item_stock");
			}
		} catch (SQLException e) {

		}

		int current_stock = dbstock + quan;

		// update stock and price
		try {
			PreparedStatement ps1 = con
					.prepareStatement("UPDATE item "
							+ "SET item_stock = ?, item_price = ?"
							+ "WHERE upc = ?");

			ps1.setInt(1, current_stock);
			ps1.setFloat(2, price);
			ps1.setString(3, upc);
			ps1.executeQuery();
			
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
				throw new Exception("Error with updating item quantity and price");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	
}
