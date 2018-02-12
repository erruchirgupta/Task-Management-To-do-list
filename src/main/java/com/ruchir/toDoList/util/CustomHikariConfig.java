package com.ruchir.toDoList.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.zaxxer.hikari.HikariConfig;

/**
 * 
 * @author	Ruchir Gupta
 * @email	erruchirgupta@gmail.com
 * @date	11:44:25 PM
 * 
 **/

public class CustomHikariConfig extends HikariConfig {

	   private static byte[] sharedvector = {
	          0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11
	          };
	   
	   	@Override
		public void setPassword(String password) {
			super.setPassword(decryptText(password));
		}
	
	   /**
	    * This method accepts a raw text as a input and encrypt the text 
	    * using MD5 encryption
	    * 
	    * @param rawText input text that needs to be encrypted
	    * @return returns a MD5 encrypted text
	    */
	   /**
	 * @param rawText
	 * @return
	 */
	public String encryptText(String rawText)
	    {
	        String encText = "";
	        byte[] keyArray = new byte[24];
	        byte[] temporaryKey;
	        String key = "sensitivepassphrasetext";
	        byte[] toEncryptArray = null;
	  
	        try
	        {
	            toEncryptArray =  rawText.getBytes("UTF-8");
	            MessageDigest m = MessageDigest.getInstance("MD5");
	            temporaryKey = m.digest(key.getBytes("UTF-8"));
	 
	            if(temporaryKey.length < 24) // DESede require 24 byte length key
	            {
	                int index = 0;
	                for(int i=temporaryKey.length;i< 24;i++)
	                {                   
	                    keyArray[i] =  temporaryKey[index];
	                }
	            }        
	 
	            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");            
	            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));            
	            byte[] encrypted = c.doFinal(toEncryptArray);            
	            encText = new String(Base64.getEncoder().encode(encrypted));
	 
	        }
	        catch(NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx)
	        {
	            NoEx.printStackTrace();
	        }
	 
	        return encText;        
	    }
	   
	   /**
	    * This method accepts a encrypted text as a input and decrypts the text 
	    * using MD5 decryption
	    * 
	    * @param encText input text that needs to be encrypted
	    * @return returns a MD5 encrypted text
	    */
	   /**
	 * @param encText
	 * @return
	 */
	public String decryptText(String encText)
	    {
	        String rawText = "";
	        byte[] keyArray = new byte[24];
	        byte[] temporaryKey;
	        String key = "sensitivepassphrasetext";
	    
	        try
	        {
	            MessageDigest m = MessageDigest.getInstance("MD5");
	            temporaryKey = m.digest(key.getBytes("UTF-8"));           
	 
	            if(temporaryKey.length < 24) // DESede require 24 byte length key
	            {
	                int index = 0;
	                for(int i=temporaryKey.length;i< 24;i++)
	                {                  
	                    keyArray[i] =  temporaryKey[index];
	                }
	            }
	            
	            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
	            byte[] decrypted = c.doFinal(Base64.getDecoder().decode(encText));
	 
	            rawText = new String(decrypted, "UTF-8");                    
	        }
	        catch(NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx)
	        {
	            NoEx.printStackTrace();
	        }      
	 
	        return rawText; 
	    }
	  
	   /**
	 * @param a
	 */
	public static void main(String[] a){
			CustomHikariConfig object = new CustomHikariConfig();
			System.out.println(object.encryptText("root"));
	   }
	}