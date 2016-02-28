package com.dbutil;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class CommonUtil extends DBUtil {
	private static final String NAME = "name";
	private static final String VALUE = "value";
	
	public CommonUtil(){
		this.tableName = "common";
	}
	
	public int getNewPort() throws ClassNotFoundException, JSONException, SQLException{
		JSONStringer json = new JSONStringer();
		json.object();
		json.key(NAME).value("port");
		json.endObject();
		JSONArray arr = executeQuery(genQuery(tableName, null, new JSONObject(json.toString()), null, null));
		if(arr.length()>0){
			JSONObject info = arr.getJSONObject(0);
			int port = info.getInt(VALUE);
			
			JSONStringer input = new JSONStringer();
			input.object();
			input.key(VALUE).value(port+1);
			input.endObject();
			JSONStringer where = new JSONStringer();
			where.object();
			where.key(NAME).value("port");
			where.endObject();
			executeUpdate(genUpdate(tableName, new JSONObject(input.toString()),new JSONObject(where.toString())));
			return port+1;
		}
		else{
			JSONStringer input = new JSONStringer();
			input.object();
			input.key(NAME).value("port");
			input.key(VALUE).value(30000);
			input.endObject();
			executeUpdate(genInsert(tableName, new JSONObject(input.toString())));
			return 30000;
		}
	}
}
