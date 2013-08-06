package com.ubc.ca.service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		
		;
		java.sql.Date jday = new java.sql.Date(d.getTime());
		ps.setDate(1, jday);

		ResultSet rs = ps.executeQuery();		// get the list of sold items
		
		while (rs.next()) {
			Item i;
			try {
				i = new Item(rs.getString("upc"), rs.getString("item_category"), 
						rs.getInt("units"), rs.getFloat("item_price"));
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
	
}
