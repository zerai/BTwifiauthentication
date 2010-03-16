package org.bobarctor.Rm3Wifi.Persistence;

import org.bobarctor.Rm3Wifi.Model.Data;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class XMLHandler extends DefaultHandler{
	/**
	 * @author Michele Valentini
	 * 
	 */
     // ===========================================================
     // Fields
     // ===========================================================
     
     @SuppressWarnings("unused")
	 private boolean in_data = false;
     private boolean in_username = false;
     private boolean in_password = false;
 	 private static final String TAG = "Rm3WiFi";

     private Data data = new Data();
     // ===========================================================
     // Getter & Setter
     // ===========================================================

     public Data getParsedData() {
    	 return this.data;
     }

     // ===========================================================
     // Methods
     // ===========================================================
     @Override
     public void startDocument() throws SAXException {
          this.data = new Data();
     }

     @Override
     public void endDocument() throws SAXException {
          // Nothing to do
     }

     /** Gets be called on opening tags like:
      * <tag>
      * Can provide attribute(s), when xml was like:
      * <tag attribute="attributeValue">*/
     @Override
     public void startElement(String namespaceURI, String localName,
               String qName, Attributes atts) throws SAXException {
          if (localName.equals("data")) {
               this.in_data = true;
          }else if (localName.equals("username")) {
               this.in_username = true;
               
          }else if (localName.equals("password")) {
               this.in_password = true;
          }
          
     }
     
     /** Gets be called on closing tags like:
      * </tag> */
     @Override
     public void endElement(String namespaceURI, String localName, String qName)
               throws SAXException {
          if (localName.equals("data")) {
               this.in_data = false;
          }else if (localName.equals("username")) {
               this.in_username = false;
          }else if (localName.equals("password")) {
               this.in_password = false;
          }
     }
     
     /** Gets be called on the following structure:
      * <tag>characters</tag> */
     @Override
    public void characters(char ch[], int start, int length) {
          if(this.in_password){
          data.setPassword(new String(ch, start, length));
          Log.d(TAG, data.getPassword());
     }else if(this.in_username){
    	 data.setUsername(new String(ch, start, length));
    	 Log.d(TAG, data.getUsername());
     }
    }
}