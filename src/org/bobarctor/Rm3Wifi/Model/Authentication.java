package org.bobarctor.Rm3Wifi.Model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.bobarctor.Rm3Wifi.Exceptions.LoginException;
import org.bobarctor.Rm3Wifi.Exceptions.LogoutException;
import org.bobarctor.Rm3Wifi.utils.EasySSLSocketFactory;


import android.util.Log;

public class Authentication {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static final String TAG = "Rm3WiFi:Authentication";

	public static void Authenticate(Data data,String ip) throws LoginException{
		Log.i(TAG,"Authenticate()");
		HttpClient httpclient = getClient(); 
        HttpPost httppost = new HttpPost("https://authentication.uniroma3.it/login.pl"); 
        try { 
            // Add your data 
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
            nameValuePairs.add(new BasicNameValuePair("_FORM_SUBMIT","1"));
            nameValuePairs.add(new BasicNameValuePair("which_form","reg"));
            nameValuePairs.add(new BasicNameValuePair("destination",""));
            nameValuePairs.add(new BasicNameValuePair("source",ip));
            nameValuePairs.add(new BasicNameValuePair("bs_name", data.getUsername())); 
            nameValuePairs.add(new BasicNameValuePair("bs_password", data.getPassword())); 
            Log.i(TAG, "nameValuePairs added");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
            Log.i(TAG, "entity added");
            // Execute HTTP Post Request 
            HttpResponse response = httpclient.execute(httppost); 
            Log.i(TAG, "http executed");
            Log.d(TAG, response.toString());             
        } catch (ClientProtocolException e) { 
          Log.e(TAG, "Errore nel client protocol: " + e.getMessage()); 
          throw new LoginException("Authentication " + e.getMessage());
        } catch (UnknownHostException e) { 
          Log.e(TAG, "UnknownHostException: " + e.getMessage()); 
          throw new LoginException("Authentication " + e.getMessage());
        } catch (IOException e){
          Log.e(TAG, "IOException: "+e.getMessage());
          throw new LoginException("Authentication " + e.getMessage());
        } 
	}
	
	 public static DefaultHttpClient getClient() {
		 Log.i(TAG,"getClient()");
		 DefaultHttpClient ret = null;

	        // sets up parameters
	     HttpParams params = new BasicHttpParams();
	     HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	     HttpProtocolParams.setContentCharset(params, "utf-8");
	     params.setBooleanParameter("http.protocol.expect-continue", false);
	        
	        // registers schemes for both http and https
	     SchemeRegistry registry = new SchemeRegistry();
	     registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	     registry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
	     ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params,registry);
	     ret = new DefaultHttpClient(manager, params);
	     return ret;
	    }
	 
	 public static void logout() throws LogoutException{
		 Log.i(TAG,"logout()");
		 HttpClient client = getClient();
		 HttpGet get = new HttpGet("http://logout.wifi-uniroma3.it/");
		 try {
			client.execute(get);
		} catch (UnknownHostException e) { 
	        Log.e(TAG, "UnknownHostException: "+e.getMessage()); 
	        throw new LogoutException("Authentication " + e.getMessage());
	    } catch (ClientProtocolException e) {
			Log.e(TAG, "Errore nel client protocol: "+e.getMessage()); 
			throw new LogoutException("Authentication " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "IOException: "+e.getMessage() ); 
			throw new LogoutException("Authentication " + e.getMessage());
		} 
	 }
}
