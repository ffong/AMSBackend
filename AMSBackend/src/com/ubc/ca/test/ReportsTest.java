package com.ubc.ca.test;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ubc.ca.model.Item;
import com.ubc.ca.model.Purchase;
import com.ubc.ca.service.ConnectionService;
import com.ubc.ca.service.ProductService;
import com.ubc.ca.service.ReportService;

public class ReportsTest {
	
	static Connection con;
	
	@BeforeClass
	public static void setUp() throws ConnectException {
		ConnectionService cs = new ConnectionService();
		con = ConnectionService.getConnection();
	}
	
	
	// hacky 
	@Test
	public void dailyReportTest() {
		try {
			ReportService rs = new ReportService();
			Date d = new SimpleDateFormat("dd-MMM-yyyy").parse("13-Aug-2013");
			List<Item> items = rs.generateDailySalesReport(d);
			printDailyReport(items);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());			
		}
		
	}
	
	
	private void printDailyReport(List<Item> items) {
		String FORMAT = "%16s%16s%16s%16s%16s\n";
		System.out.format(FORMAT, "UPC", "Category", "Unit Price", "Units", "Total Value");
		for (Item i : items) {
			System.out.format(FORMAT, i.getUPC(), i.getCategory(), i.getPrice(), i.getQuantity(), i.getTotalPrice());
		}
	}
	
}
