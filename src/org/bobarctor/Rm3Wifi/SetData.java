package org.bobarctor.Rm3Wifi;



import org.bobarctor.Rm3Wifi.Exceptions.SaveDataException;
import org.bobarctor.Rm3Wifi.Model.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetData extends Activity{
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static final String TAG = "Rm3WiFi:SetData";
	private static final int MILLISECONDS = 100;
	private static final String savePath="data";
	private EditText username;
	private EditText password;
	//private FacadeController fc;
	private Vibrator vibrator;
	private ProgressDialog myPd;
	private SharedPreferences sharedPref;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.i(TAG,"onCreate()");
		setContentView(R.layout.set_data);
		 Typeface face=Typeface.createFromAsset(getAssets(), "fonts/Purisa.ttf"); 
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); 
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		myPd = new ProgressDialog(SetData.this);
		// Dichiarazione ed assegnazione dei listener del pulsante
		Button save = (Button)findViewById(R.id.save);
		save.setTypeface(face);
		TextView setDataTitle = (TextView)findViewById(R.id.set_data_title);
		setDataTitle.setTypeface(face);
		TextView usernameText =  (TextView)findViewById(R.id.username_text);
		usernameText.setTypeface(face);
		TextView passwordText =  (TextView)findViewById(R.id.password_text);
		passwordText.setTypeface(face);
		password.setTypeface(face);
		username.setTypeface(face);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveButtonClick();
			}
		});
	}

//	public void saveButtonClick() {
//		Log.d(TAG, "saveButtonClick() " + username.getText() + " " + password.getText()  );
//		myPd.setMessage(getResources().getString(R.string.saving_data));
//    	myPd.show();
//    	new Thread(new Runnable(){
//    		private final static String TAG = "threadSaveButtonClick()";
//    		@Override
//    		public void run(){
//    			Log.i(TAG,"run()");
//    			try{
//    				Log.i(TAG,"try");
//    				SetData.this.fc.saveData(new Data(username.getText().toString(), password.getText().toString()));
//    				saveDataHandler.sendEmptyMessage(1);
//    			}catch(SaveDataException e){
//    				e.getMessage();
//					saveDataHandler.sendEmptyMessage(0);
//					Log.e(TAG,"SaveDataException: ");
//    			}finally{
//    				Log.i(TAG,"finally");
//    				SetData.this.finish();
//    			}
//    		}
//    	}).start();	
//	}

	public void saveButtonClick() {
		Log.d(TAG, "saveButtonClick() " + username.getText() + " " + password.getText()  );
		myPd.setMessage(getResources().getString(R.string.saving_data));
    	myPd.show();
    	new Thread(new Runnable(){
    		private final static String TAG = "threadSaveButtonClick()";
    		@Override
    		public void run(){
    			Log.i(TAG,"run()");
    			try{
    				Log.i(TAG,"try");
    				SetData.this.saveData(new Data(username.getText().toString(), password.getText().toString()));
    				saveDataHandler.sendEmptyMessage(1);
    			}catch(SaveDataException e){
					saveDataHandler.sendEmptyMessage(0);
					Log.e(TAG,"SaveDataException: " + e.getMessage());
    			}finally{
    				Log.i(TAG,"finally");
    				SetData.this.finish();
    			}
    		}
    	}).start();	
	}
	
	private Handler saveDataHandler = new Handler(){
		private final static String TAG = "saveDataHandler:";
		@Override
		public void handleMessage(Message msg){
			Log.i(TAG,"handleMessage");
			myPd.dismiss();
			if (msg.what==1){
				Log.d(TAG,"msg.what==1");
				vibra();
				Toast toast = Toast.makeText(
						SetData.this,
						R.string.save_data_successful,
						Toast.LENGTH_SHORT
				);
				toast.show();
			}else{
				Log.d(TAG,"msg.what!=1");
				vibra();
				Toast toast = Toast.makeText(
						SetData.this,
						R.string.save_data_error,
						Toast.LENGTH_SHORT
				);
				toast.show();
			}
		}
	};
	
	private void saveData(Data data) throws SaveDataException{
		Log.i(TAG,"saveData()");
		sharedPref = this.getSharedPreferences(SetData.savePath, Activity.MODE_WORLD_WRITEABLE);
		editor=sharedPref.edit();
		editor.putString("username", data.getUsername());
		editor.putString("password", data.getPassword());
		if (!editor.commit()){
			throw new SaveDataException("Data not saved");
		}
	}
	
	private void vibra(){
		Log.i(TAG,"vibra()");
    	vibrator.vibrate(SetData.MILLISECONDS);
    }
}
