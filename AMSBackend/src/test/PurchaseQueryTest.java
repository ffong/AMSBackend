package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import main.BasicQuery;
import main.Purchase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseQueryTest {
	
	private static Connection con;
	private static TestQueries tq;
	private static Purchase pq;
	
	@BeforeClass
	public static void setUp() {
		con = BasicQuery.getConnection();
		tq = new TestQueries(con);
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date d = new java.util.Date();
		System.out.println(formatter.format(d));
	}
	
	//@Test
	public void getReceiptIdTest() {
		try {
			String id = pq.getNextReceiptId();
			System.out.println(id);
			Assert.assertNotNull(id);	
			
		} catch (SQLException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void cardInStorePurchaseTest() {
		try {
			tq.executeSpecialQuery("INSERT into Item values ('100', 'title', 'dvd', 'category', 'company', 1999, 12.95)");
			tq.executeSpecialQuery("INSERT into Item values ('200', 'title2', 'cd', 'category', 'company', 1999, 12.95)");
			
			Purchase p = new Purchase("123214432", "15-SEP-1014"); // credit card number and expiry date
			p.addPurchaseItem("100", 1);
			p.addPurchaseItem("200", 2);
			p.commitPurchase();
			
			tq.printTable("Purchase");
			tq.printTable("PurchaseItem");
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
}
