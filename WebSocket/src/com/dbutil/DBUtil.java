package com.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.common.CommonSetting;

public class DBUtil {
	private Connection con;
	
	public String tableName;
	
	public DBUtil(){
	}
	
	
	private void openConn() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		if(this.con == null){
			this.con = DriverManager.getConnection(CommonSetting.JDBC_CONN_KEY);
		}
	}
	
	private void closeConn() throws SQLException{
		if(this.con != null){
			this.con.close();
			this.con = null;
		}
	}
	
	
	public JSONArray executeQuery(String sql) throws SQLException, ClassNotFoundException{
		openConn();
		Statement stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		JSONStringer arr = new JSONStringer();
		arr.array();
		String[] columnName = null;
		int[] columnType = null;
		while(rs.next()){
			ResultSetMetaData rsmd =rs.getMetaData();
			int count = rsmd.getColumnCount();
			if(columnName == null && columnType == null){
				if(columnName == null){
					columnName = new String[count];
				}
				if(columnType == null){
					columnType = new int[count];
				}
				for (int i = 0;i<count;i++){
					columnName[i] = rsmd.getColumnName(i+1);
					columnType[i] = rsmd.getColumnType(i+1);
				}
			}
			arr.object();
			for (int i = 0;i<count;i++){
				int type = columnType[i];
				arr.key(columnName[i]);
				switch (type) {
				case Types.BIGINT:
				case Types.INTEGER:
				case Types.TINYINT:
				case Types.SMALLINT:
					arr.value(rs.getLong(i+1));
					break;
				case Types.DOUBLE:
				case Types.FLOAT:
					arr.value(rs.getDouble(i+1));
					break;
				default:
					arr.value(rs.getObject(i+1));
					break;
				}
			}
			arr.endObject();
		}
		arr.endArray();
		rs.close();
		stmt.close();
		closeConn();
		return new JSONArray(arr.toString());
	}
	
	public void executeUpdate(String sql) throws SQLException, ClassNotFoundException{
		openConn();
		Statement stmt = this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		stmt.executeUpdate(sql);
		stmt.close();
		closeConn();
	}
	
	public String genInsert(String table,JSONObject obj){
		String keys = "";
		String values = "";
		JSONArray names = obj.names();
		for(int i = 0; i<names.length();i++){
			String key = names.getString(i);
			keys += key;
			values += "\'" + obj.get(key).toString() + "\'";
			if(i!=names.length()-1){
				keys += ",";
				values += ",";
			}
		}
		String result = String.format(CommonSetting.INSERT_SQL,table,keys,values);
		System.out.println(result);
		return result;
	}
	
	public String genUpdate(String table,JSONObject datas,JSONObject wheres){
		String data = "";
		String where = "";
		JSONArray names = datas.names();
		for(int i = 0; i<names.length();i++){
			String key = names.getString(i);
			data += key + "=" + datas.get(key).toString(); 
			if(i!=names.length()-1){
				data += ",";
			}
		}
		JSONArray names2 = wheres.names();
		for(int i = 0; i<names2.length();i++){
			String key = names2.getString(i);
			where += key + "=" + "\'" + wheres.get(key).toString()  + "\'"; 
			if(i!=names2.length()-1){
				where += " and ";
			}
		}
		String result = String.format(CommonSetting.UPDATE_SQL,table,data,where);
		return result;
	}
	
	public String genDelete(String table,JSONObject wheres){
		String where = "";
		JSONArray names2 = wheres.names();
		for(int i = 0; i<names2.length();i++){
			String key = names2.getString(i);
			where += key + "=" + "\'" + wheres.get(key).toString() + "\'"; 
			if(i!=names2.length()-1){
				where += " and ";
			}
		}
		String result = String.format(CommonSetting.DELETE_SQL,table,where);
		return result;
	}
	
	public String genQuery(String table,JSONArray keys,JSONObject wheres,JSONArray orderKeys,JSONArray groupKeys){
		String key = "";
		String where = "";
		if(keys!=null && keys.length() > 0){
			for(int i = 0; i < keys.length();i++){
				key += keys.getString(i);
				if(i!=keys.length()-1){
					key += ",";
				}
			}
		}
		else{
			key = "*";
		}
		JSONArray names2 = wheres.names();
		for(int i = 0; i<names2.length();i++){
			String _key = names2.getString(i);
			where += _key + "=" + "\'" + wheres.get(_key).toString() + "\'"; 
			if(i!=names2.length()-1){
				where += " and ";
			}
		}
		String result = String.format(CommonSetting.QUERY_SQL,key,table,where);
		if(orderKeys!=null){
			String orderBy = "";
			for(int i = 0; i < orderKeys.length();i++){
				orderBy += orderKeys.getString(i);
				if(i!=orderKeys.length()-1){
					orderBy += ",";
				}
			}
			result += String.format(CommonSetting.ORDER_BY_SQL, orderBy);
		}
		if(groupKeys!=null){
			String groupBy = "";
			for(int i = 0; i < groupKeys.length();i++){
				groupBy += groupKeys.getString(i);
				if(i!=groupKeys.length()-1){
					groupBy += ",";
				}
			}
			result += String.format(CommonSetting.GROUP_BY_SQL, groupBy);
		}
		return result;
	}
}
