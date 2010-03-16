package org.bobarctor.Rm3Wifi.Exceptions;

public class LoginException extends Exception {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static final long serialVersionUID = -9033225185432769447L;
	private String errore;
	private final String EXCEPTION_NAME = "LoginException";
	
	public LoginException(){
		super();
		this.errore = "unknown";
	}
	
	public LoginException(String error){
		super(error);
		this.errore = error;
	}
	
	@Override
	public String getMessage(){
		return this.errore;
	}
	
	@Override
	public String toString(){
		return EXCEPTION_NAME + this.errore;
	}
}
