package org.bobarctor.Rm3Wifi.Facade;

import org.bobarctor.Rm3Wifi.Exceptions.LoginException;
import org.bobarctor.Rm3Wifi.Exceptions.LogoutException;

import org.bobarctor.Rm3Wifi.Model.Authentication;
import org.bobarctor.Rm3Wifi.Model.Data;

import android.util.Log;

public class FacadeController {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static FacadeController instance;
	private static final String TAG = "Rm3WiFi:FacadeController";
	
	private FacadeController(){
		
	}
	
	public void login(Data data, String ip) throws LoginException{
		try{
			Log.i(TAG,"login()");
			Log.d(TAG, data.toString());
			Authentication.Authenticate(data,ip);
		} catch(LoginException e){
			Log.e(TAG, "LoginException: " + e.getMessage());
        	throw new LoginException("FacadeController " + e.getMessage());	
		}
	}
	
	public void logout() throws LogoutException{
		Log.i(TAG,"logout()");
		try{
			Log.d(TAG,"logout");
			Authentication.logout();
		}catch(LogoutException e){
			Log.d(TAG,"LogoutException: " + e.getMessage());
			throw new LogoutException("FacadeController " + e.getMessage());
		}
	}
	
	/** Restituisce l'unica istanza di FacadeController */
	public static FacadeController getInstance(){
		Log.i(TAG,"getInstance()");
		if (instance==null) { instance = new FacadeController();}
		return instance;
	}
}
