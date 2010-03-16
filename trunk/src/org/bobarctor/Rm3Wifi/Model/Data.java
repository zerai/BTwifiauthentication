package org.bobarctor.Rm3Wifi.Model;

public class Data {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private String username;
	private String password;
	
	public Data(){}
	
	public Data(String username, String password){
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString(){
		return "Username: " + this.getUsername() + " Password: "+ this.getPassword();
	}
	
	

}
