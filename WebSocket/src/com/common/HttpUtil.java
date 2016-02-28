package com.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class HttpUtil {
	
	public static String doPost(String url,String param) throws Exception{
		String parameterData = param;
        
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Accept","application/json");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterData.length()));
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            
            outputStreamWriter.write(parameterData.toString());
            outputStreamWriter.flush();
            
            
            if (httpURLConnection.getResponseCode() >= 300) {
            	InputStream errStream = httpURLConnection.getErrorStream();
            	InputStreamReader errStreamReader = new InputStreamReader(errStream);
            	BufferedReader errReader = new BufferedReader(errStreamReader);
            	String errStr = "";
            	String errTemp;
            	while((errTemp = errReader.readLine())!=null){
            		errStr += errTemp;
            	}
            	System.out.println(errStr);
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            
        } finally {
            
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }
        return resultBuffer.toString();
	}
	
	public static String doGet(String urls) throws Exception{
		URL url = new URL(urls);
		HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("Accept","application/json");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String tempLine = null;
        if (conn.getResponseCode() >= 300) {
        	InputStream errStream = conn.getErrorStream();
        	InputStreamReader errStreamReader = new InputStreamReader(errStream);
        	BufferedReader errReader = new BufferedReader(errStreamReader);
        	String errStr = "";
        	String errTemp;
        	while((errTemp = errReader.readLine())!=null){
        		errStr += errTemp;
        	}
        	System.out.println(errStr);
            throw new Exception("HTTP Request is not success, Response code is " + conn.getResponseCode());
        }
        inputStream = conn.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);
        String result = "";
        while ((tempLine = reader.readLine()) != null) {
        	System.out.println(tempLine);
        	result += tempLine;
        }
        if (reader != null) {
            reader.close();
        }
        
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        
        if (inputStream != null) {
            inputStream.close();
        }
        
        result = ChangeCharset.toUTF_8(result);
		return result;
	}
	
}
