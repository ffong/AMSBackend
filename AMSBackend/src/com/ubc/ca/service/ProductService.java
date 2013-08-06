package com.ubc.ca.service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.ubc.ca.exception.NoStockException;
import com.ubc.ca.exception.TooManyItemsFoundException;
import com.ubc.ca.model.Item;

public class ProductService {
	
	public ArrayList<Item> searchProduct(String category,String title,String leadSinger,int quantity,ArrayList<Item> searchList) throws ConnectException, SQLException
	{
		searchList= new ArrayList<Item>();
		System.out.println("getItem");
		Item item = null;
		Connection con=ConnectionService.getConnection();
		String query=null;
		PreparedStatement query_ps=null;
		if(!(leadSinger.equals("")||null==leadSinger|| category.equals("")||null==category || title.equals("")||null==title))
		{
			
		query="SELECT item.upc,title,item_stock,item_category FROM item,leadsinger where item.upc=leadsinger.upc and item.item_category=? and item.title=? and leadsinger.singer_name=?";
			
		query_ps= con.prepareStatement(query);

		query_ps.setString(1, category);
		query_ps.setString(2, title);

		query_ps.setString(3, leadSinger);
		
		}
		else if(!(category.equals("")||null==category|| title.equals("")||null==title))
		{
			
		query="SELECT item.upc,title,item_stock,item_category,item_price FROM item where item.title=? and item_category=?";
			
		query_ps= con.prepareStatement(query);

		query_ps.setString(1, title);
		query_ps.setString(2, category);
		}
		else if(!(leadSinger.equals("")||null==leadSinger|| title.equals("")||null==title))
		{
			
		query="SELECT item.upc,title,item_stock,item_category,item_price FROM item,leadsinger where item.upc=leadsinger.upc and item.title=? and leadsinger.singer_name=?";
			
		query_ps= con.prepareStatement(query);

		query_ps.setString(1, title);
		query_ps.setString(2, leadSinger);
		}
		else if(!(leadSinger.equals("")||null==leadSinger|| category.equals("")||null==category))
		{
			
		query="SELECT item.upc,title,item_stock,item_category,item_price FROM item,leadsinger where item.upc=leadsinger.upc and item.item_category=? and leadsinger.singer_name=?";
			
		query_ps= con.prepareStatement(query);

		query_ps.setString(1, category);
		query_ps.setString(2, leadSinger);
		}
		if(!(category.equals("")||null==category))
		{
			query="SELECT item.upc,title,item_stock,item_category,item_price FROM item where item_category=?";
			query_ps= con.prepareStatement(query);
			query_ps.setString(1, category);
		}
		if(!(title.equals("")||null==title))
		{
			query="SELECT item.upc,title,item_stock,item_category,item_price FROM item where item.title=?";
			query_ps= con.prepareStatement(query);

			query_ps.setString(1, title);

		}
		if(!(leadSinger.equals("")||null==leadSinger))
		{
			query="SELECT item.upc,title,item_stock,item_category,item_price FROM item,leadsinger where item.upc=leadsinger.upc and leadsinger.singer_name=?";
			query_ps= con.prepareStatement(query);

			query_ps.setString(1, leadSinger);

		}
		
		
		
		ResultSet rs=query_ps.executeQuery();
		 if(rs!=null)
			 {
			 while(rs.next())
		 {
		    
		   
		    	item=new Item();
		    	item.setPrice(rs.getFloat(5));
		    	item.setCategory(rs.getString(4)); 
		    	item.setQuantity(rs.getInt(3));
		    	item.setTitle(rs.getString(2));
		    	item.setUPC(Integer.toString(rs.getInt(1)));
		   
	            searchList.add(item);
		}
		
			 }
		
		
		con.commit();
		return searchList;
		
	}
	
	public Item getItem(String category,String title,String leadSinger,int quantity) throws ConnectException , SQLException, TooManyItemsFoundException, NoStockException
	{
		System.out.println("getItem");
		Item item = null;
		Connection con=ConnectionService.getConnection();
		String query=null;
		String countquery=null;
		PreparedStatement query_ps=null;
		PreparedStatement query_ct=null;
		if(!(leadSinger.equals("")||null==leadSinger|| category.equals("")||null==category || title.equals("")||null==title))
		{
			
		query="SELECT item.upc,title,item_stock,item_category FROM item,leadsinger where item.upc=leadsinger.upc and item.item_category=? and item.title=? and leadsinger.singer_name=?";
		countquery="SELECT count(*) FROM item where item.title=? and item_category=?";	
			
		query_ps= con.prepareStatement(query);
		query_ct=con.prepareStatement(countquery);

		query_ps.setString(1, category);
		query_ps.setString(2, title);

		query_ps.setString(3, leadSinger);
		query_ct.setString(1, category);
		query_ct.setString(2, title);

		query_ct.setString(3, leadSinger);
		}
		else if(!(category.equals("")||null==category|| title.equals("")||null==title))
		{
			
		query="SELECT item.upc,title,item_stock,item_category,item_price FROM item where item.title=? and item_category=?";
		countquery="SELECT count(*) FROM item where item.title=? and item_category=?";	
			
		query_ps= con.prepareStatement(query);
		query_ct=con.prepareStatement(countquery);

		query_ps.setString(1, title);
		query_ps.setString(2, category);
		query_ct.setString(1, title);
		query_ct.setString(2, category);
		}
		else if(!(leadSinger.equals("")||null==leadSinger|| title.equals("")||null==title))
		{
			
		query="SELECT item.upc,title,item_stock,item_category,item_price FROM item,leadsinger where item.upc=leadsinger.upc and item.title=? and leadsinger.singer_name=?";
		countquery="SELECT count(*) FROM item where item.title=? and item_category=?";	
			
		query_ps= con.prepareStatement(query);
		query_ct=con.prepareStatement(countquery);

		query_ps.setString(1, title);
		query_ps.setString(2, leadSinger);
		query_ct.setString(1, title);
		query_ct.setString(2, leadSinger);
		}
		else if(!(leadSinger.equals("")||null==leadSinger|| category.equals("")||null==category))
		{
			
		query="SELECT item.upc,title,item_stock,item_category,item_price FROM item,leadsinger where item.upc=leadsinger.upc and item.item_category=? and leadsinger.singer_name=?";
		countquery="SELECT count(*) FROM item where item.title=? and item_category=?";	
			
		query_ps= con.prepareStatement(query);
		query_ct=con.prepareStatement(countquery);

		query_ps.setString(1, category);
		query_ps.setString(2, leadSinger);
		query_ct.setString(1, category);
		query_ct.setString(2, leadSinger);
		}
		if(!(category.equals("")||null==category))
		{
			query="SELECT item.upc,title,item_stock,item_category,item_price FROM item where item_category=?";
			countquery="SELECT count(*) FROM item where item_category=?";
			query_ps= con.prepareStatement(query);
			query_ps.setString(1, category);
			query_ct=con.prepareStatement(countquery);
			query_ct.setString(1, category);
		}
		if(!(title.equals("")||null==title))
		{
			query="SELECT item.upc,title,item_stock,item_category,item_price FROM item where item.title=?";
			countquery="SELECT count(*) FROM item where item.title=?";
			query_ps= con.prepareStatement(query);
			query_ct=con.prepareStatement(countquery);

			query_ps.setString(1, title);
			query_ct.setString(1, title);

		}
		if(!(leadSinger.equals("")||null==leadSinger))
		{
			query="SELECT item.upc,title,item_stock,item_category,item_price FROM item,leadsinger where item.upc=leadsinger.upc and leadsinger.singer_name=?";
			countquery="SELECT count(*) FROM item where FROM item,leadsinger where item.upc=leadsinger.upc and leadsinger.singer_name=?";
			query_ps= con.prepareStatement(query);
			query_ct=con.prepareStatement(countquery);

			query_ps.setString(1, leadSinger);
			query_ct.setString(1, leadSinger);

		}
		
		
		ResultSet rs=query_ct.executeQuery();
		if(rs!=null)
		{
			rs.next();
			if(rs.getInt(1)>1)
			{
				throw new TooManyItemsFoundException();
			}
		}
		  rs=query_ps.executeQuery();
		 if(rs!=null)
		 {
		    rs.next();
		   
		    	item=new Item();
		    	item.setPrice(rs.getFloat(5));
		    	item.setCategory(rs.getString(4)); 
		    	item.setQuantity(rs.getInt(3));
		    	item.setTitle(rs.getString(2));
		    	item.setUPC(Integer.toString(rs.getInt(1)));
		   
	
			  
		    	updateStock(item, quantity);	
		    	if(item.getQuantity()<quantity)
				  {
					  item.setErrorMessage("Requested Quantity Not Available. Quantity set to Available stock");
					  item.setQuantity(item.getQuantity());
				  }
				  else
				  {
				   item.setQuantity(quantity);
				  }

		  
		}
		
	   
		
		
		con.commit();
		return item;
	}
    
	public List getItemList(String category, String title)
	{
		ArrayList<Item> searchedItems= new ArrayList<Item>();
		return searchedItems;
	}
	
	public Item addItemtoCart(Item item) throws NoStockException
	{
		
		return item;
	}
	
	
	public String saveOrder(float totalprice,String customerId) throws ConnectException, SQLException
	{
		Connection con= ConnectionService.getConnection();
		String receiptId=null;
		String updateQuery="INSERT INTO PURCHASE VALUES(receiptid.nextval,?,?,?,?,?,?)";
		String generatedColumns[] = {"RECEIPTID"};
		PreparedStatement query_update=con.prepareStatement(updateQuery,generatedColumns);
		Calendar cal= new GregorianCalendar();
		query_update.setDate(1, new Date(cal.getTimeInMillis()));
		query_update.setString(2, customerId);
		query_update.setString(3, "1234567890123456");
		cal.add(Calendar.MONTH, 5);
		cal=getExpectedDate();
		query_update.setDate(4, new Date(cal.getTimeInMillis()));
		query_update.setDate(5, new Date(cal.getTimeInMillis()));
		query_update.setNull(6, java.sql.Types.NULL);
		query_update.executeQuery();
		ResultSet rs=query_update.getGeneratedKeys();
		if(rs!=null)
		{
			rs.next();
			receiptId=Integer.toString((rs.getInt(1)));
		}
		return receiptId;
	}
	
	public Calendar getExpectedDate() throws SQLException, ConnectException
	{
		Connection con= ConnectionService.getConnection();
		String query="select count(*), expected_date from  purchase where DELIVERY_DATE is null group by EXPECTED_DATE having EXPECTED_DATE=(select EXPECTED_DATE from PURCHASE where RECEIPTID=(SELECT MAX(RECEIPTID) from PURCHASE))";
		PreparedStatement query_ps=con.prepareStatement(query);
		Calendar cal=new GregorianCalendar();
		ResultSet rs=query_ps.executeQuery();
		while(rs.next())
		{
			if(rs.getInt(1)<3)
			{
				Date date=rs.getDate(2);
				
				//long j= date.getTime()-cal.getTimeInMillis();
				cal.setTimeInMillis(date.getTime());
			}
			else
			{
				Date date=rs.getDate(2);
				cal.setTimeInMillis(date.getTime());
				cal.add(Calendar.DATE, 1);
			}
		}
		return cal;
	}
	
	public int updateStock(Item item,int quantity) throws ConnectException, SQLException
	{
		Connection con=ConnectionService.getConnection();
		  String query="update item set item_stock=? where upc=?";
		  PreparedStatement query_ps= con.prepareStatement(query);
		  int j=0;
		  if(item.getQuantity()<quantity)
		  {
			  j=0;
			  item.setErrorMessage("Requested Quantity Not Available. Quantity set to Available stock");
			  item.setQuantity(item.getQuantity());
		  }
		  else
		  {
		  j=item.getQuantity()-quantity;
		  System.out.println(j);
		  item.setQuantity(quantity);
		  }
			query_ps.setInt(1,j);
			query_ps.setString(2,item.getUPC());

			int i =query_ps.executeUpdate();
			System.out.println(i);
			con.commit();
			return i;
	}
	
	public void savePurchasedItems(ArrayList<Item> shoppingCart,int receiptid) 
	{
		Connection con=null;
		try {
			con = ConnectionService.getConnection();
			String query= "INSERT INTO PURCHASEITEM VALUES(?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			
			for(Item items : shoppingCart )
			{
				ps.setInt(1, receiptid);
				ps.setInt(2, Integer.parseInt(items.getUPC()));
				ps.setInt(3, items.getQuantity());
				ps.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
