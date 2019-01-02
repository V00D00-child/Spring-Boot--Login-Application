package com.bowmanidris.model;

/*
 * The AppUser class represents for a record in the  APP_USER table of database.
 */

public class AppUser {
	
	private  Long userId;
	private String userName;
	private String encrytedPassword;
	
	public AppUser() {
		
	}
	
	public AppUser(Long userId, String userName, String encrytedPassword) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.encrytedPassword = encrytedPassword;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEncrytedPassword() {
		return encrytedPassword;
	}


	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}
	
	@Override
    public String toString() {
        return this.userName + "/" + this.encrytedPassword;
    }
	
	
	
	

}
