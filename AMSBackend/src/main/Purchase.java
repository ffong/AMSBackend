package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Purchase extends BasicQuery {

	private String id;
	private String purchaseDate; 
	private String cid;
	private String cardno;
	private String expectedDate;
	private String expiryDate;
	private String deliveryDate;
	private HashMap<String, Integer> items;
	
	private static Connection con;
	public static LinkedHashMap<String,String> PURCHASE_SCHEMA = new LinkedHashMap<String, String>();
	static {
		PURCHASE_SCHEMA.put("RECEIPTID", "NUMBER");
		PURCHASE_SCHEMA.put("PURCHASE_DATE", "DATE");
		PURCHASE_SCHEMA.put("CID", "NUMBER");
		PURCHASE_SCHEMA.put("CARD_NO", "NUMBER");
		PURCHASE_SCHEMA.put("EXPIRY_DATE", "DATE");
		PURCHASE_SCHEMA.put("EXPECTED_DATE", "DATE");
		PURCHASE_SCHEMA.put("DELIVERY_DATE", "DATE");
	}
	
	// just create id and purchase date
	public Purchase() {
		try {
			con = getConnection();
			id = getNextReceiptId();
			items = new HashMap<String, Integer>();
			setPurchaseDateToToday();
			
		} catch (SQLException e) {
			System.out.println("Unable to create Purchase.");
			e.printStackTrace();
		}
	}
	
	// basically an in store purchase. new class??? 
	public Purchase(String cardno, String expiryDate) {
		try {
			con = getConnection();
			id = getNextReceiptId();
			setPurchaseDateToToday();
			cid = null;
			this.cardno = cardno;
			this.expiryDate = expiryDate;
			deliveryDate = null;
			items = new HashMap<String, Integer>();
			
		} catch (SQLException e) {
			System.out.println("Unable to create Purchase.");
			e.printStackTrace();
		}
	}
	
	// initialize everything but id and purchase date
	public Purchase(String cid, String cardno, String expiryDate, String deliveryDate) {
		try {
			con = getConnection();
			id = getNextReceiptId();
			setPurchaseDateToToday();
			this.cid = cid;
			this.cardno = cardno;
			this.expiryDate = expiryDate;
			this.deliveryDate = deliveryDate;
			items = new HashMap<String, Integer>();
			
		} catch (SQLException e) {
			System.out.println("Unable to create Purchase.");
			e.printStackTrace();
		}
	}
	
	public void commitPurchase() {
		insertTuple2("Purchase", PURCHASE_SCHEMA, new String[] {id, purchaseDate, cid, cardno, expectedDate, expiryDate, deliveryDate});
		
		// insert into purchase item tables
		for (String upc : items.keySet()) {
			String qty = Integer.toString(items.get(upc));
			insertTuple("PurchaseItem", new String[] {id, upc, qty});
		}	
		
		// decrement stock
		
		// show receipt
	}

	/**
	 * Gets the next key from purchase_sequence in the database. 
	 * @return next receiptId  
	 * @throws SQLException 
	 */
	public String getNextReceiptId() throws SQLException{
		String id = null;
		Statement s;
		ResultSet rs;

		s = con.createStatement();
		rs = s.executeQuery("SELECT PURCHASE_SEQUENCE.nextval from dual"); // get the next key
		while (rs.next()) {
			int i = rs.getInt("NEXTVAL");
			id = Integer.toString(i);
		}
		System.out.println(id);
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setPurchaseDateToToday() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date d = new java.util.Date();
		this.purchaseDate = formatter.format(d);
	}
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public HashMap<String, Integer> getItems() {
		return items;
	}

	public void setPurchaseItems(HashMap<String, Integer> items) {
		this.items = items;
	}
	
	public void addPurchaseItem(String upc, int qty) {
		items.put(upc, qty);
	}
}
