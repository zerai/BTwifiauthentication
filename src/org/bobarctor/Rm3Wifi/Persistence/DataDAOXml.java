package org.bobarctor.Rm3Wifi.Persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.bobarctor.Rm3Wifi.Exceptions.LoadDataException;
import org.bobarctor.Rm3Wifi.Exceptions.SaveDataException;
import org.bobarctor.Rm3Wifi.Model.Data;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import android.util.Log;





public class DataDAOXml implements DataDAO {
	/**
	 * @author Michele Valentini
	 * 
	 */
	private static final String TAG = "Rm3WiFi:DAO";
	private final String FILENAME = "/sdcard/rm3wifiauthentication.xml";

	public DataDAOXml(){
		Log.i(TAG,"DataDAOXml()");
	}
	
	private void createFile(String username, String password) throws IOException{
		Log.i(TAG,"createFile()");
		File f=new File(this.FILENAME);
		f.delete();
		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(f));
		PrintStream ps=new PrintStream(bos);
		ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ps.println("<data>");
		ps.println("\t<username>" + username + "</username>");
		ps.println("\t<password>" + password + "</password>");
		ps.println("</data>");
		ps.close();
		bos.close();
	}
	
	@Override
	public Data loadData() throws LoadDataException {
		Log.i(TAG,"loadData()");
		Data data = new Data();
		try{
			File f = new File(this.FILENAME);
			if (!f.exists()){
				createFile("username","password"); 
				Log.d(TAG, "creo il file " + f.toString());
			}
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(this.FILENAME));
			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
			/* Create a new ContentHandler and apply it to the XML-Reader */
			XMLHandler handler = new XMLHandler();
			xr.setContentHandler(handler);
			xr.parse(new InputSource(buf));
			data = handler.getParsedData();
			Log.d(TAG, "Username: "+ data.getUsername() + " ,Password: " + data.getPassword());
			buf.close();
		}catch(IOException e){
			Log.e(TAG,"IOException: " + e.getMessage());
			throw new LoadDataException("DataDAOXml " + e.getMessage());
		} catch (SAXException e) {
			Log.e(TAG,"SAXException: " + e.getMessage());
			throw new LoadDataException("DataDAOXml " + e.getMessage());
		} catch (ParserConfigurationException e) {
			Log.e(TAG,"ParserConfigurationException: " + e.getMessage());
			throw new LoadDataException("DataDAOXml " + e.getMessage());
		}
		return data;
	}

	@Override
	public void saveData(Data data) throws SaveDataException {
		Log.i(TAG,"saveData()");
		try{
			createFile(data.getUsername(), data.getPassword());
		}catch(IOException e){
			Log.e(TAG,"IOException: "+e.getMessage());
			throw new SaveDataException("DataDAOXml " + e.getMessage());
		}
	}
}
