package com.dbutil;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class ServiceUtil extends DBUtil {
	public static final String USER_ID = "user_id";
	public static final String SERVICE_ID = "service_id";
	public static final String SERVICE_NAME = "service_name";
	public static final String SERVICE_DESC = "service_desc";
	public static final String SERVICE_GIT_ID = "service_git_id";
	public static final String SERVICE_GIT_URL = "service_git_url";
	public static final String SERVICE_GIT_BRANCH = "service_git_branch";
	public static final String SERVICE_DOCKER_NUM = "service_docker_num";
	public static final String SERVICE_DOCKER_PORT = "service_docker_port";
	public static final String GIT_TYPE = "git_type";
	
	public ServiceUtil(){
		this.tableName = "service_setting";
	}
	
	public JSONArray getUserServiceList(int userId) throws ClassNotFoundException, JSONException, SQLException{
		JSONStringer json = new JSONStringer();
		json.object();
		json.key(USER_ID).value(Integer.toString(userId));
		json.endObject();
		JSONArray arr = executeQuery(genQuery(tableName,null,new JSONObject(json.toString()), null, null));
		return arr;
	}
	
	public void createNewService(int userId,String service_name,String service_desc,int service_git_id,String service_git_url,String service_git_branch,String service_docker_num,int port,int gitType) throws ClassNotFoundException, JSONException, SQLException{
		JSONStringer json = new JSONStringer();
		json.object();
		json.key(USER_ID).value(userId);
		json.key(SERVICE_NAME).value(service_name);
		json.key(SERVICE_DESC).value(service_desc);
		json.key(SERVICE_GIT_ID).value(service_git_id);
		json.key(SERVICE_GIT_URL).value(service_git_url);
		json.key(SERVICE_GIT_BRANCH).value(service_git_branch);
		json.key(SERVICE_DOCKER_NUM).value(service_docker_num);
		json.key(SERVICE_DOCKER_PORT).value(port);
		json.key(GIT_TYPE).value(gitType);
		json.endObject();
		executeUpdate(genInsert(tableName, new JSONObject(json.toString())));
	}
	
	public JSONObject getProjectInfo(int proj_id) throws ClassNotFoundException, JSONException, SQLException{
		JSONStringer json = new JSONStringer();
		json.object();
		json.key(SERVICE_ID).value(proj_id);
		json.endObject();
		JSONArray arr = executeQuery(genQuery(tableName,null,new JSONObject(json.toString()), null, null));
		if(arr.length()>0){
			return arr.getJSONObject(0);
		}
		return new JSONObject();
	}
	
}
