package com.ubc.ca.test;

import java.net.ConnectException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubc.ca.model.Item;
import com.ubc.ca.service.ConnectionService;
import com.ubc.ca.service.ReportService;

public class ReportsTest {
	
	static Connection con;
	
	@BeforeClass
	public static void setUp() throws ConnectException {
		ConnectionService.getConnection();
	}
	
	
	// hacky 
	@Test
	public void dailyReportTest() {
		try {
			ReportService rs = new ReportService();
			Date d = new SimpleDateFormat("dd-MMM-yyyy").parse("10-Aug-2013");
			List<Item> items = rs.generateDailySalesReport(d);
			printDailyReport(items);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());			
		}	
	}
	
	@Test 
	public void bestSellerReportTest() {
		try {
			ReportService rs = new ReportService();
			Date d = new SimpleDateFormat("dd-MMM-yyyy").parse("13-Aug-2013");
			List<Item> items = rs.generateTopSellersReport(d, 3);
			printTopSellerReport(items);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());			
		}
	}
	
	private void printDailyReport(List<Item> items) {
		String FORMAT = "%-16s%-16s%-16s%-16s%-16s\n";
		System.out.format(FORMAT, "UPC", "Category", "Unit Price", "Units", "Total Value");
		for (Item i : items) {
			System.out.format(FORMAT, i.getUPC(), i.getCategory(), i.getPrice(), i.getQuantity(), i.getTotalPrice());
		}
	}
	
	// title, the company, the current stock and the number of copies sold
	private void printTopSellerReport(List<Item> items) {
		String FORMAT = "%-24s%-16s%-16s%-16s\n";
		System.out.format(FORMAT, "Title", "Company", "Current Stock", "Units Sold");
		for (Item i : items) {
			System.out.format(FORMAT, i.getTitle(), i.getCompany(), i.getQuantity(), i.getQtySold());
		}
	}
	
}
