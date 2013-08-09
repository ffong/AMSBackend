package com.ubc.ca.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubc.ca.model.Return;
import com.ubc.ca.service.ConnectionService;
import com.ubc.ca.service.ReturnService;

public class ReturnTest {
	static ReturnService rs;
	
	@BeforeClass
	public static void setUp() {
		rs = new ReturnService();
	}
	
	@Test
	public void returnDayTest() throws ParseException {
		GregorianCalendar curCal = new GregorianCalendar();
		java.sql.Date cur = utilToSQLDate(curCal.getTime());
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		java.sql.Date a = utilToSQLDate(cal.getTime());
		Assert.assertTrue(rs.isValidReturn(a, cur));		
		
		GregorianCalendar cal2 = new GregorianCalendar();		
		cal2.add(Calendar.DAY_OF_MONTH, -16);
		java.sql.Date a2 = utilToSQLDate(cal2.getTime());
		Assert.assertFalse(rs.isValidReturn(a2, cur));
		
		GregorianCalendar cal3 = new GregorianCalendar();		
		cal2.add(Calendar.DAY_OF_MONTH, -15);
		java.sql.Date a3 = utilToSQLDate(cal3.getTime());
		Assert.assertTrue(rs.isValidReturn(a3, cur));
	}
	
	@Test
	public void returnMonthTest() throws ParseException {
		GregorianCalendar curCal = new GregorianCalendar();
		java.sql.Date cur = utilToSQLDate(curCal.getTime());
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.MONTH, -1);
		java.sql.Date a = utilToSQLDate(cal.getTime());
		Assert.assertFalse(rs.isValidReturn(a, cur));		
	}
	
	@Test
	public void returnYearTest() throws ParseException {
		GregorianCalendar curCal = new GregorianCalendar();
		java.sql.Date cur = utilToSQLDate(curCal.getTime());
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.YEAR, -1);
		java.sql.Date a = utilToSQLDate(cal.getTime());
		Assert.assertFalse(rs.isValidReturn(a, cur));		
	}
	
	@Test
	public void invalidUPCReturnTest(){
		try {
			rs.checkAndProcessReturn(0, 5, "123");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("No such purchase exists"));
		}
	}
	
	@Test
	public void invalidDateReturnTest() throws SQLException{
		int start_stock = 0;
		try {
			start_stock = getStock("15");
			rs.checkAndProcessReturn(1, 2, "15");
			Assert.fail("Date should be invalid.");
		}
		catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Return is invalid"));
			Assert.assertEquals(start_stock, getStock("15"));
		}
	}
	
	@Test
	public void invalidQuantityReturnTest() throws SQLException{
		int start_stock = 0;
		try {
			start_stock = getStock("15");
			rs.checkAndProcessReturn(1, 5, "15");
			Assert.fail("Quantity should be invalid.");
		}
		catch (Exception e) {
			Assert.assertEquals(start_stock, getStock("15"));
			Assert.assertTrue(e.getMessage().contains("Returning more items than purchased"));
		}
	}
	
	@Test
	public void validReturnTest() {
		int start_stock = 0;
		try {
			start_stock = getStock("5666");
			Return r = new Return(9, "5666", 10);
			r.saveReturn();

			Assert.assertEquals(start_stock + 10, getStock("5666"));
			Assert.assertTrue(checkReturnExists(r));
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void validReturnTest2() {
		int start_stock = 0;
		try {
			start_stock = getStock("25");
			Return r = new Return(5, "25", 4);
			r.saveReturn();

			Assert.assertEquals(start_stock + 4, getStock("25"));
			Assert.assertTrue(checkReturnExists(r));
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	private java.sql.Date utilToSQLDate(java.util.Date d) {
		return new java.sql.Date(d.getTime());
	}
	
	private boolean checkReturnExists(Return ret) throws SQLException {
		Connection con = ConnectionService.getConnection();
		PreparedStatement ps = con.prepareStatement(
				"SELECT R.retid, upc, receiptid, quantity " +
				"FROM Return R, ReturnItem RI " +
				"WHERE R.retid=RI.retid AND R.retid=? AND " +
				"quantity=? AND receiptid=? AND upc=?");
		
		ps.setInt(1, ret.getRetid());
		ps.setInt(2, ret.getQuantity());
		ps.setInt(3, ret.getReceiptid());
		ps.setString(4, ret.getUpc());
		ResultSet rs = ps.executeQuery();		
		
		return rs.next();
	}
	
	private int getStock(String upc) throws SQLException {
		Connection con = ConnectionService.getConnection();
		PreparedStatement ps = con.prepareStatement(
				"SELECT item_stock " +
				"FROM Item " +
				"WHERE upc=?");
		ps.setString(1, upc);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt(1);
	}
}
