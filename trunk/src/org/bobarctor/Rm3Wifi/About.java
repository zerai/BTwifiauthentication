package org.bobarctor.Rm3Wifi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class About extends Activity {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static final String TAG = "Rm3WiFi:About";

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.i(TAG,"onCreate()");
		setContentView(R.layout.about);
	}

}
