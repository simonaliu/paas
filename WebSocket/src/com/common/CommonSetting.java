package com.common;

public class CommonSetting {
	/**
	 * 数据库相关
	 * 
	 * */
	public static final String MY_SQL_USER_NAME = "root";
	
	public static final String MY_SQL_USER_PSW = "root";
	
	public static final String JDBC_SERVER_HOST = "159.226.57.12";
	
	public static final String JDBC_SERVER_PORT = "3306";
	
	public static final String JDBC_SERVER_SQL_NAME = "docker";
	
	public static final String JDBC_CONN_KEY = "jdbc:mysql://"+JDBC_SERVER_HOST+":"+JDBC_SERVER_PORT+"/"+JDBC_SERVER_SQL_NAME+"?user="+MY_SQL_USER_NAME+"&password="+MY_SQL_USER_PSW;
	
	public static final String INSERT_SQL = "insert into %s (%s) values(%s)";
	
	public static final String UPDATE_SQL = "update %s set %s where %s";
	
	public static final String DELETE_SQL = "delete from %s where %s";
	
	public static final String QUERY_SQL = "select %s from %s where %s";
	
	public static final String ORDER_BY_SQL = "order by %s";
	
	public static final String GROUP_BY_SQL = "group by %s";
	
	/**
	 * git相关
	 */
	public static final String GITLAB_APPLICATION = "http://159.226.57.11/api/v3";
	
	public static final String GITLAB_URL_OAUTH = "http://159.226.57.11/oauth/authorize?client_id=8c39223511a78c4a996ef0791b135ee3bfad824c393b974311bf561ae8a9a14d&redirect_uri=http://127.0.0.1:8081/WebSocket/Oauth&response_type=code";
	
	public static final String GITLAB_URL_ACCESS = "http://159.226.57.11/oauth/token";
	
	public static final String GITLAB_ACCESS = "client_id=8c39223511a78c4a996ef0791b135ee3bfad824c393b974311bf561ae8a9a14d&client_secret=34c56d8d0444b65424e725fac0d696c4891781f2233c10b3c9913184890c7ebd&redirect_uri=http://127.0.0.1:8081/WebSocket/Oauth&grant_type=authorization_code";
	
	public static final String GITHUB_APPLICATION = "https://api.github.com";
	
	public static final String GITHUB_URL_OAUTH = "https://github.com/login/oauth/authorize?client_id=e47b5960b871b34838b0&redirect_uri=http://127.0.0.1:8081/WebSocket/OauthGitHub";
	
	public static final String GITHUB_URL_ACCESS = "https://github.com/login/oauth/access_token";
	
	public static final String GITHUB_ACCESS = "client_id=e47b5960b871b34838b0&client_secret=29aac8bcc5b936fbc664f935181e63543c70dde3";
	
	public static final String GITHUB_CLIENT_ID = "e47b5960b871b34838b0";
	
	public static final String GITHUB_CLIENT_SECRET = "29aac8bcc5b936fbc664f935181e63543c70dde3";
}
