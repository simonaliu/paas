package com.dbutil;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class UserUtil extends DBUtil {
	
	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";
	public static final String USER_PSW = "user_psw";
	
	public UserUtil() {
		this.tableName = "userinfo";
	}
	
	public boolean registerNewUser(String username,String userpsw) throws SQLException, ClassNotFoundException{
		int userId = loginCheck(username, userpsw);
		if(userId != -1) return false;
		JSONStringer json = new JSONStringer();
		json.object();
		json.key(USER_NAME).value(username);
		json.key(USER_PSW).value(userpsw);
		json.endObject();
		JSONObject object = new JSONObject(json.toString());
		executeUpdate(genInsert(tableName, object));
		return true;
	}
	
	public int loginCheck(String username,String userpsw) throws SQLException, ClassNotFoundException, JSONException{
		JSONStringer keys = new JSONStringer();
		keys.array();
		keys.value(USER_ID);
		keys.endArray();
		
		JSONStringer where = new JSONStringer();
		where.object();
		where.key(USER_NAME).value(username);
		where.key(USER_PSW).value(userpsw);
		where.endObject();
		
		JSONArray arr = executeQuery(genQuery(tableName, new JSONArray(keys.toString()), new JSONObject(where.toString()), null, null));
		
		if(arr.length()>0){
			return arr.getJSONObject(0).getInt(USER_ID);
		}
		
		return -1;
	}
	
	public Boolean nameCheck(String username) throws SQLException, ClassNotFoundException, JSONException{
		JSONStringer where = new JSONStringer();
		where.object();
		where.key(USER_NAME).value(username);
		where.endObject();
		
		JSONArray arr = executeQuery(genQuery(tableName, null, new JSONObject(where.toString()), null, null));
		
		if(arr.length()>0){
			return true;
		}
		
		return false;
	}
}
