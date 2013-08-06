package com.ubc.ca.model;

import java.net.ConnectException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ubc.ca.service.ProductService;


/**
 * 
 * @author Makthum
 * This Model class is associated with order save and holds values for Order Confirmation page
 */

public class Purchase {

	
	/**
	 * These attributes corresponds to fields displayed on the order confirmation page 
	 * 
	 */
	private String customerId;
	private float  totalprice;
	private Date   purchasedDate;
	private ArrayList<Item> shoppingCart= new ArrayList<Item>();
	private String receiptId;
	
	
	//Error Message for Order Confirmation Page 
	private String errorMessage;
	
	
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public float getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}
	public Date getPurchasedDate() {
		return purchasedDate;
	}
	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}
	public ArrayList<Item> getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ArrayList<Item> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	/**
	 * This method is invoked when pay button is clicked. This method populates the fields on the Order confirmation page and 
	 * creates a new order ,retrieves receiptId from back end and displays it.
	 * @return  String : returns Action outcome based on which page navigation is decided 
	 */
	
	public String generateOrder()
	{
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		// Session object holds current username 
		//String userId=(String) session.getAttribute("username");
		String userId = "userId";
		
		ProductService service = new ProductService();
		
		try {
			
			this.receiptId= service.saveOrder(totalprice, customerId);
			this.customerId=userId;		
			this.purchasedDate=new Date(System.currentTimeMillis());
			service.savePurchasedItems(shoppingCart, Integer.parseInt(receiptId));
			
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			this.errorMessage=e.getMessage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			this.errorMessage=e.getMessage();
		}
		return "orderConfirmation";
	}
	
}
