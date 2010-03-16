package org.bobarctor.Rm3Wifi.Facade;



import org.bobarctor.Rm3Wifi.Exceptions.LoadDataException;
import org.bobarctor.Rm3Wifi.Exceptions.LoginException;
import org.bobarctor.Rm3Wifi.Exceptions.LogoutException;
import org.bobarctor.Rm3Wifi.Exceptions.SaveDataException;

import org.bobarctor.Rm3Wifi.Model.Authentication;
import org.bobarctor.Rm3Wifi.Model.Data;

import org.bobarctor.Rm3Wifi.Persistence.DataDAO;
import org.bobarctor.Rm3Wifi.Persistence.DataDAOXml;
import android.util.Log;


public class FacadeController {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static FacadeController instance;
	private static final String TAG = "Rm3WiFi:FacadeController";
	private DataDAO dd;
	
	private FacadeController(){
		
	}
	
	public void init(){
		Log.i(TAG, "FacadeController: Init");
		this.dd = new DataDAOXml();
	}
	
	public void saveData(Data data) throws SaveDataException{
		Log.d(TAG, data.toString());
		try{
			dd.saveData(data);
		}catch(SaveDataException e){
			Log.e(TAG,"SaveDataException: " + e.getMessage());
			throw new SaveDataException("FacadeController " + e.getMessage());	
		}
	}
	
	public Data getData() throws LoadDataException{
		Data data= new Data();
		try{
			data = dd.loadData();
			Log.d(TAG,data.toString());
		}catch(LoadDataException e){
			Log.e(TAG, "LoadDataException: " + e.getMessage());
			throw new LoadDataException("FacadeController " + e.getMessage());
		}
		return data;
	}
	
	public void login(String ip) throws LoginException{
        try{
            Log.i(TAG, "login()");
            Data d  = this.getData();
            Log.d(TAG, d.toString());
            // Authentication.Authenticate(d);
            Authentication.Authenticate(d,ip);
            }catch(LoginException e){
            	Log.e(TAG, "LoginException: " + e.getMessage());
            	throw new LoginException("FacadeController " + e.getMessage());	
            }catch(LoadDataException e){
            	Log.e(TAG,"LoadDataException: " + e.getMessage());
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
