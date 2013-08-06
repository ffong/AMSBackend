package com.ubc.ca.service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ubc.ca.model.*;

public class ReportService {
	private static Connection con;

	public ReportService() throws ConnectException {
		con = ConnectionService.getConnection();
	}
	
	public List<Item> generateDailySalesReport(Date d) throws SQLException, ParseException {
		List<Item> soldItems = new ArrayList<Item>();		
		PreparedStatement ps = con.prepareStatement(
				"SELECT I.upc, item_category, item_price, SUM(quantity) AS units " +
				"FROM Item I, Purchase P, PurchaseItem PI " +
				"WHERE I.upc = PI.upc AND P.receiptId = PI.receiptId AND P.purchase_date = ? " +
				"GROUP BY I.upc, item_category, item_price");
		
		java.sql.Date jday = new java.sql.Date(d.getTime());
		ps.setDate(1, jday);

		ResultSet rs = ps.executeQuery();		// get the list of sold items
		
		// convert each result to item
		while (rs.next()) {
			try {
				String upc = rs.getString("upc");
				String categ = rs.getString("item_category");
				int qty = rs.getInt("units");
				float price = rs.getFloat("item_price");
				
				Item i = new Item();
				i.setUPC(upc);
				i.setCategory(categ);
				i.setQuantity(qty);
				i.setPrice(price);
				i.setTotalPrice(price * qty);
				
				soldItems.add(i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	

		return soldItems;
	}
	
	/* Top Selling Items: The user provides a date and a number, say n. The system prints a list of the n 
	 * best selling items on that day. For each best seller, the system shows the title, the company, the 
	 * current stock and the number of copies sold. The output should be ordered according to sales: the 
	 * best selling item should be first, the second best will follow, etc. */
	
	public List<Item> generateTopSellersReport(Date d, int n) throws SQLException{
		List<Item> items = new ArrayList<Item>();
		
		PreparedStatement ps = con.prepareStatement
				("SELECT * FROM " +
				"(SELECT title, company, item_stock, SUM(quantity) AS sold " +
				"FROM Item I, Purchase P, PurchaseItem PI " +
				"WHERE I.upc = PI.upc AND P.receiptid = PI.receiptid AND P.purchase_date=?" +
				"GROUP BY item_stock, title, company " +
				"ORDER BY sold DESC) " +
				"WHERE ROWNUM<=?");
		
		java.sql.Date jday = new java.sql.Date(d.getTime());
		ps.setDate(1, jday);
		ps.setInt(2, n);
		
		// go through the result, creating Items and adding them to the list
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String title = rs.getString("title");
			String company = rs.getString("company");
			int stock = rs.getInt("item_stock");
			int qtySold = rs.getInt("sold");
			
			Item i = new Item();
			i.setTitle(title);
			i.setCompany(company);
			i.setQuantity(stock);
			i.setQtySold(qtySold);
			
			items.add(i);
		}
		
		return items;
	}
	
}
