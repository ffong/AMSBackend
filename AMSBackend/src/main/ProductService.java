package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProductService extends BasicQuery {
	
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
	
	public ProductService() {
		con = BasicQuery.getConnection();
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
		return id;
	}

	/**
	 * Insert the Purchase into the table
	 * @param p
	 * @throws SQLException 
	 */
	public void savePurchaseToDB(Purchase p) throws SQLException {
		String id = getNextReceiptId();
		
		// insert into Purchase table
		insertTuple2("Purchase", PURCHASE_SCHEMA, new String[] {id, p.getPurchaseDate(), 
				p.getCid(), p.getCardno(), p.getExpectedDate(), p.getExpiryDate(), p.getDeliveryDate()});
		
		// insert into purchase item tables
		HashMap<String, Integer> items = p.getItems();
		for (String upc : items.keySet()) {
			String qty = Integer.toString(items.get(upc));
			insertTuple("PurchaseItem", new String[] {id, upc, qty});
		}	
		
		// decrement stock
		
		// show receipt
	}
}
