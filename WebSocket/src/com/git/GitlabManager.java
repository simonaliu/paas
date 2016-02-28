package com.git;

import org.json.JSONArray;
import org.json.JSONObject;

import com.common.CommonSetting;
import com.common.HttpUtil;

public class GitlabManager {

	private String oauth_token;
	private String access_token;
	private String private_token;
	private int type;
	
	public static final int GITLAB = 1;
	
	public static final int GITHUB = 2;
	
	/**
	 * gitlab
	 */
	private static final String LIST_PROJECT = "/projects";
	
	private static final String SINGLE_PROJECT = "/projects/";
	
	private static final String LIST_BRANCH = "/projects/%d/repository/branches";
	
	private static final String SINGLE_BRANCH = "/projects/%d/repository/branches/%s";
	
	/**
	 * github
	 */
	private static final String LIST_REPOS = "/user/repos";
	
	private static final String SINGLE_REPOS = "/repos/";
	
	private static final String LIST_GITHUB_BRANCH = "/repos/%s/%s/branches";
	
	private static final String SINGLE_GITHUB_BRANCH = "/repos/%s/%s/branches/%s";
	
	private JSONObject github_user;
	
	/**
	 * 共用
	 */
	private static final String USER = "/user";
	
	public GitlabManager(int type){
		this.type = type;
	}
	
	public void setOauthCode(String code){
		this.oauth_token = code;
	}
	
	public void setAccessCode(String code){
		this.access_token = code;
	}
	
	public void getAccessToken() throws Exception{
		if(type == GITLAB){
			String parameterData = CommonSetting.GITLAB_ACCESS + "&code=" + this.oauth_token;
	        String result = HttpUtil.doPost(CommonSetting.GITLAB_URL_ACCESS, parameterData);
	        
	        JSONObject access = new JSONObject(result);
	        
	        this.access_token = access.getString("access_token");
		}
		else if(type == GITHUB){
			String parameterData = CommonSetting.GITHUB_ACCESS + "&code=" + this.oauth_token;
	        String result = HttpUtil.doPost(CommonSetting.GITHUB_URL_ACCESS, parameterData);
	        
	        JSONObject access = new JSONObject(result);
	        
	        this.access_token = access.getString("access_token");
	        
	        
	        //System.out.println(userObj.toString(2));
		}
	}
	
	public boolean isAccess(){
		return access_token!=null&&!access_token.equals("");
	}
	
	public void getUserToken() throws Exception{
		String url = CommonSetting.GITLAB_APPLICATION + USER + genOauthToken();
		String result = HttpUtil.doGet(url);
		
		System.out.println(result);
	}
	
	@SuppressWarnings("unused")
	private String genPrivateToken(){
		return "?private_token=" + this.private_token; 
	}
	
	private String genOauthToken(){
		return "?access_token=" + this.access_token; 
	}
	
	public String getOauthToken(){
		return this.access_token; 
	}
	
	public JSONArray getProjectList() throws Exception{
		if(type == GITLAB){
			String api = CommonSetting.GITLAB_APPLICATION + LIST_PROJECT+genOauthToken();
			String result = HttpUtil.doGet(api);
			JSONArray arr = new JSONArray(result);
			System.out.println(result);
			return arr;
		}
		else if(type == GITHUB){
			String api = CommonSetting.GITHUB_APPLICATION + LIST_REPOS+genOauthToken();
			String result = HttpUtil.doGet(api);
			String substr = result.substring(10650, 10750);
			System.out.println(substr);
			JSONArray arr = new JSONArray(result);
			System.out.println(arr.toString(2));
			return arr;
		}
		return null;
	}
	
	public JSONObject getProject(int proj_id) throws Exception{
		if(type == GITLAB){
			System.out.println(proj_id);
			String api = CommonSetting.GITLAB_APPLICATION + SINGLE_PROJECT +  proj_id + genOauthToken();
			String result = HttpUtil.doGet(api);
			System.out.println(result);
			JSONObject obj = new JSONObject(result);
			return obj;
		}
		return null;
	}
	
	public JSONObject getProject(String proj_name) throws Exception{
		if(type == GITHUB){
			String userParam = USER+genOauthToken();
	        String userInfo = HttpUtil.doGet(CommonSetting.GITHUB_APPLICATION+userParam);
	        github_user = new JSONObject(userInfo);
			String api = CommonSetting.GITHUB_APPLICATION + SINGLE_REPOS + String.format("%s/%s", github_user.getString("login"),proj_name) + genOauthToken();
			System.out.println(api);
			String result = HttpUtil.doGet(api);
			JSONObject obj = new JSONObject(result);

			System.out.println(obj.toString(3));
			return obj;
		}
		return null;
	}
	
	public JSONArray getProjectBranches(int proj_id) throws Exception{
		if(type == GITLAB){
			String api = CommonSetting.GITLAB_APPLICATION + String.format(LIST_BRANCH,proj_id) + genOauthToken();
			String result = HttpUtil.doGet(api);
			System.out.println(result);
			JSONArray arr = new JSONArray(result);
			return arr;
		}
		return null;
	}
	
	public JSONArray getProjectBranches(String name) throws Exception{
		if(type == GITHUB){
			String userParam = USER+genOauthToken();
	        String userInfo = HttpUtil.doGet(CommonSetting.GITHUB_APPLICATION+userParam);
	        github_user = new JSONObject(userInfo);
			String api = CommonSetting.GITHUB_APPLICATION + String.format(LIST_GITHUB_BRANCH,github_user.getString("login"),name) + genOauthToken();
			System.out.println(api);
			String result = HttpUtil.doGet(api);
			JSONArray arr = new JSONArray(result);
			return arr;
		}
		return null;
	}
	
	public JSONObject getProjectBranch(int proj_id,String name) throws Exception{
		if(type == GITLAB){
			String api = CommonSetting.GITLAB_APPLICATION + String.format(SINGLE_BRANCH, proj_id,name) + genOauthToken();
			String result = HttpUtil.doGet(api);
			System.out.println(result);
			JSONObject obj = new JSONObject(result);
			return obj;
		}
		return null;
	}
	
	public JSONObject getProjectBranch(String proj_name,String name) throws Exception{
		if(type == GITHUB){
			String userParam = USER+genOauthToken();
	        String userInfo = HttpUtil.doGet(CommonSetting.GITHUB_APPLICATION+userParam);
	        github_user = new JSONObject(userInfo);
			String api = CommonSetting.GITHUB_APPLICATION + String.format(SINGLE_GITHUB_BRANCH, github_user.getString("login"),proj_name,name) + genOauthToken();
			String result = HttpUtil.doGet(api);
			JSONObject obj = new JSONObject(result);
			System.out.println(obj.toString(4));
			return obj;
		}
		return null;
	}
}
