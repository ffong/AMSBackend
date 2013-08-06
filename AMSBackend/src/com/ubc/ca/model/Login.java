package com.ubc.ca.model;


import java.net.ConnectException;

import com.ubc.ca.exception.InvalidLoginException;
import com.ubc.ca.service.LoginService;

/**
 * 
 * @author Makthum
 * This Class takes care of user authentication and session validation.
 */

public class Login {
	
	
private String userId;
private String password;


private String errorMessage=null;




public String getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(String errorMessa) {
	this.errorMessage = errorMessage;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}


/**
 * 
 * @return Action outcome which determines page navigation from index.jsp
 */
public String login()
{
		String au=null;
		LoginService service = new LoginService();
		
		 try {
			au=service.authenticate(this.userId,this.password);
			System.out.println(au);
			if(!au.equals("failure"))
			{
				//FacesContext fc=FacesContext.getCurrentInstance();			
				//HttpSession session= (HttpSession) fc.getExternalContext().getSession(true);
				//session.setAttribute("username",this.userId);
			}
		} 
		 catch (ConnectException e) {
			// TODO Auto-generated catch block
			 this.errorMessage=e.getMessage();
			e.printStackTrace();
		} catch (InvalidLoginException e) {
			// TODO Auto-generated catch block
			 this.errorMessage=e.getMessage();

			e.printStackTrace();
		}
		
	
	
	return au;
	
}
}
