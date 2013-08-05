package main;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Purchase extends BasicQuery {

	private String id;
	private String purchaseDate; 
	private String cid;
	private String cardno;
	private String expectedDate;
	private String expiryDate;
	private String deliveryDate;
	private HashMap<String, Integer> items;
	
	// just create id and purchase date
	public Purchase() {
		items = new HashMap<String, Integer>();
		setPurchaseDateToToday();
	}
	
	// basically an in store purchase. new class??? 
	public Purchase(String cardno, String expiryDate) {
		setPurchaseDateToToday();
		cid = null;
		this.cardno = cardno;
		this.expiryDate = expiryDate;
		deliveryDate = null;
		items = new HashMap<String, Integer>();
	}
	
	// initialize everything but id and purchase date
	public Purchase(String cid, String cardno, String expiryDate, String deliveryDate) {
		setPurchaseDateToToday();
		this.cid = cid;
		this.cardno = cardno;
		this.expiryDate = expiryDate;
		this.deliveryDate = deliveryDate;
		items = new HashMap<String, Integer>();
	}
	
	public void commitPurchase() throws SQLException {
		ProductService ps = new ProductService();
		ps.savePurchaseToDB(this);
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

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
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
