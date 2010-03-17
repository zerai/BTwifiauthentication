package org.bobarctor.Rm3Wifi;

import org.bobarctor.Rm3Wifi.Exceptions.LoadDataException;
import org.bobarctor.Rm3Wifi.Exceptions.LoginException;
import org.bobarctor.Rm3Wifi.Exceptions.LogoutException;
import org.bobarctor.Rm3Wifi.Facade.FacadeController;
import org.bobarctor.Rm3Wifi.Model.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.util.Log;

public class Main extends Activity{
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static final String TAG = "Rm3WiFi:Main";
	private static final int MILLISECONDS = 100;
	private static final String savePath="data";
	private FacadeController fc;
	private Vibrator vibrator;
	private ProgressDialog myPd;
	private SharedPreferences sharedPref;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.main);
        fc = FacadeController.getInstance();
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); 
        myPd = new ProgressDialog(Main.this);

        // Dichiarazione ed assegnazione dei listener dei pulsanti
        View loginButton = findViewById(R.id.login_button);
//        View setDataButton = findViewById(R.id.set_data_button);
//        View aboutButton = findViewById(R.id.about_button);
        View exitButton = findViewById(R.id.exit_button);
        View logoutButton = findViewById(R.id.logout_button);
        
        exitButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		exitButtonClick();
        	}
        });
//        aboutButton.setOnClickListener(new OnClickListener() {
//        	@Override
//        	public void onClick(View v) {
//        		aboutButtonClick();
//        	}
//        });
//        setDataButton.setOnClickListener(new OnClickListener() {
//        	@Override
//        	public void onClick(View v) {
//        		setDataButtonClick();
//        	}
//        });
        loginButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		loginButtonClick();
        	}
        });
        logoutButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		logoutButtonClick();
        	}
        });
    }
    
    private void exitButtonClick(){
    	Log.i(TAG, "exitButtonClick()");
    	finish();
    }
    
//    private void aboutButtonClick(){
//    	Log.d(TAG, "About");
//		Intent i = new Intent(this, About.class);
//		startActivity(i);
//    }
    
//    private void setDataButtonClick(){
//    	Log.d(TAG, "SetData");
//		Intent i = new Intent(this, SetData.class);
//		startActivity(i);
//    }
    
    private void loginButtonClick(){
    	Log.i(TAG,"loginButtonClick()");
    	myPd.setMessage(getResources().getString(R.string.login_progress));
    	myPd.show();
    	new Thread(new Runnable(){
    		private final static String TAG = "threadLoginButtonClick";
    		@Override
    		public void run(){
    			Log.i(TAG, "run()");
    			try{
    				Log.i(TAG, "try");
    				fc.login(loadData(),getWifiIp());
    				loginHandler.sendEmptyMessage(1);
    			} catch(LoginException e){
    				Log.e(TAG,"LoginException: " + e.getMessage());
    				loginHandler.sendEmptyMessage(0);
    			} catch(LoadDataException e){
    				Log.e(TAG,"LoadDataException: " + e.getMessage());
    				loginHandler.sendEmptyMessage(0);
    			}
    		}
    	}).start();
    }
    
    private void logoutButtonClick(){
    	Log.i(TAG,"logoutButtonClick()");
    	myPd.setMessage(getResources().getString(R.string.logout_progress));
    	myPd.show();
    	new Thread(new Runnable(){
    		private final static String TAG = "threadLogoutButtonClick";
    		@Override
    		public void run(){
    			Log.i(TAG,"run()");
    			try{
    				Log.i(TAG,"try");
    				//fc.login();
    				fc.logout();
    				logoutHandler.sendEmptyMessage(1);
    			} catch(LogoutException e){
    				Log.e(TAG,"LogoutException: " + e.getMessage());
    				logoutHandler.sendEmptyMessage(0);
    			}
    		}
    	}).start();
    }
    
    private Handler loginHandler = new Handler(){
    	private final static String TAG = "loginHandler:";
    		@Override
    		public void handleMessage(Message msg){
    			Log.i(TAG, "handleMessage");
    			myPd.dismiss();
    			if (msg.what==1){
    				Log.d(TAG,"msg.what==1");
    				vibra();
    				Toast toast = Toast.makeText(
    						Main.this,
    						R.string.login_successful,
    						Toast.LENGTH_SHORT
    				);
    				toast.show();
    			}else{
    				Log.d(TAG,"msg.wath!=1");
    				vibra();
    				Toast toast = Toast.makeText(
    						Main.this,
    						R.string.login_error,
    						Toast.LENGTH_SHORT
    				);
    				toast.show();
    			}
    		}
    };
    
    private Handler logoutHandler = new Handler(){
    	private final static String TAG = "logoutHandler:";
		@Override
		public void handleMessage(Message msg){
			Log.i(TAG,"handleMessage");
			myPd.dismiss();
			if (msg.what==1){
				Log.d(TAG,"msg.wath==1");
				vibra();
				Toast toast = Toast.makeText(
						Main.this,
						R.string.logout_successful,
						Toast.LENGTH_SHORT
				);
				toast.show();
			}else{
				Log.d(TAG,"msg.what!=1");
				vibra();
				Toast toast = Toast.makeText(
						Main.this,
						R.string.logout_error,
						Toast.LENGTH_SHORT
				);
				toast.show();
			}
		}
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	Log.i(TAG,"onCreateOptionsMenu");
    	super.onCreateOptionsMenu(menu);
    	int order = Menu.FIRST;
    	int GROUPA = 0;
    	int GROUPB = 1;
    	menu.add(GROUPA,order,order++,getResources().getString(R.string.set_data_label)).setIntent(new Intent(Main.this,SetData.class));
    	menu.add(GROUPB,order,order++,getResources().getString(R.string.about_label)).setIntent(new Intent(Main.this,About.class));
    	return true;
    }
    
    private String getWifiIp() { 
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE); 
        Log.i(TAG,"richiesta ip in corso...");
        int ip = wifiManager.getConnectionInfo().getIpAddress(); 
        Log.d(TAG, "ip ottenuto: "+ip);
       return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF); 
    }
    
    private Data loadData() throws LoadDataException{
		Log.i(TAG,"loadData()");
		Data data = new Data();
		try{
			sharedPref = this.getSharedPreferences(Main.savePath, Activity.MODE_WORLD_WRITEABLE);
			data = new Data(sharedPref.getString("username", "username"),sharedPref.getString("password", "password"));
			Log.d(TAG,"DATA: " + data.toString());
		}catch(ClassCastException e){
			Log.e(TAG,"ClassCastException: "+e.getMessage());
			throw new LoadDataException(e.getMessage());
		}
		return data;
	}
    
    private void vibra(){
    	Log.i(TAG, "vibra()");
    	vibrator.vibrate(Main.MILLISECONDS);
    }
}