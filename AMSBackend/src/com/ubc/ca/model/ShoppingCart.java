package com.ubc.ca.model;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



import com.ubc.ca.exception.NoStockException;
import com.ubc.ca.exception.TooManyItemsFoundException;
import com.ubc.ca.service.ProductService;

/**
 * 
 * @author Makthum
 * This Model Class takes care searching and adding items to shopping cart. All the fields on Checkout.jsp are mapped to fields in this class.
 */

public class ShoppingCart {

	
	private Item item= new Item();
	
	//holds error messages for checkout page 
	private String errorMessage=null;
	
	private String paymentMethod=null;
	private float totalprice=0;
	
	
	// Hashmap to hold the selected items from item search
	private Map<Long, Boolean> checked = new HashMap<Long, Boolean>();
	
	// Holds all items on the shopping cart
	private ArrayList<Item> shoppingcart= new ArrayList<Item>();
	
	// Holds all the items return from the search
	private ArrayList<Item> searchList= new ArrayList<Item>();
	
	public float getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<Long, Boolean> getChecked() {
		return checked;
	}

	public void setChecked(Map<Long, Boolean> checked) {
		this.checked = checked;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ArrayList<Item> getSearchList() {
		return searchList;
	}

	public void setSearchList(ArrayList<Item> searchList) {
		this.searchList = searchList;
	}

	public ArrayList<Item> getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(ArrayList<Item> shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	
	/**
	 * This method is invoked when AddtoCart button is clicked on checkout.jsp. This method takes care of searching the DB for items based on title 
	 * or category or lead singer or all of them and adds them to shopping cart. This method also updates existing stock for that particular item once selected.
	 * @return String : contains Action outcome for page navigation 
	 */
	
  public String AddCart()
  {
        ProductService service= new ProductService();
        try {
        	
        	        	
			Item item_temp=service.getItem(item.getCategory(),item.getTitle(),item.getLeadSinger(),item.getQuantity());
			
			
			// if quantity mentioned on checkout.jsp is 0 display error message without adding item to the shopping cart.
			
			if(item_temp.getQuantity()!=0)
			{
			totalprice=(item_temp.getPrice()* item_temp.getQuantity())+totalprice;
			if(!shoppingcart.contains(item_temp))
			shoppingcart.add(item_temp);
			this.errorMessage=item_temp.getErrorMessage();
			}
			else
			{
				this.errorMessage="Item Not Added. Please Specify Item Quantity More than 0";
			}
			
			
		} 
        catch (ConnectException e) {
			this.errorMessage=e.getMessage();
			e.printStackTrace();
		} 
        catch (SQLException e) {
			this.errorMessage=e.getMessage();
		} 
        catch (TooManyItemsFoundException e) {
			this.errorMessage=e.getMessage();
		}
        catch (NoStockException e) {
			this.errorMessage="Requested Quantity Not Available. Quantity set to Available stock";
		}
		  
			
	  return "Add";
  }
  
  
  /**
   * This methods search items and directs to results.jsp page 
   * @return String : Action outcome which determines page navigation
   */
  public String search()
  {
	  ProductService productService= new ProductService();
	  
	  try
	  {
		  
		this.searchList=productService.searchProduct(item.getCategory(),item.getTitle(),item.getLeadSinger(),item.getQuantity(),searchList);
	  } 
	  catch (ConnectException e) {
		this.errorMessage=e.getMessage();
	  }
	  catch (SQLException e) {
		this.errorMessage=e.getMessage();
	  }
	
	  return "search";
  }
  
  public String AddSearchedItems()
  {
	  for(Item items: searchList)
	  {
		  if (checked.get(items.getUPC())) {
			  
			  totalprice=(items.getPrice()* this.item.getQuantity())+totalprice;
			  ProductService service= new ProductService();
			  try {
				service.updateStock(items, this.item.getQuantity());
				if(!shoppingcart.contains(items))
				shoppingcart.add(items);
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	  }
	
	

	
	  return "AddSearch";
  }
  
  
}
