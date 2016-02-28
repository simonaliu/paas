package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ssh.Docker;
import ssh.JenkinsBuild;

import com.dbutil.CommonUtil;
import com.dbutil.ServiceUtil;
import com.git.GitlabManager;

@SuppressWarnings("serial")
public class ServiceServlet extends HttpServlet {
	
	public static final int GET_GIT_LIST = 1;
	public static final int CHOOSE_PROJ = 2;
	public static final int GET_BRANCHES = 3;
	public static final int CREATE_DOCKER = 4;
	public static final int GET_LIST = 5;
	public static final int OPER_PROJECT = 6;
	public static final int PROJECT_INFO = 7;
	public static final int PROJECT_OPER = 8;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession().getAttribute("userId")==null){
			PrintWriter writer = resp.getWriter();
			writer.print("-999");
			writer.flush();
			writer.close();
			return;
		}
		int oper = Integer.parseInt(req.getParameter("oper"));
		String oauth_token = req.getSession().getAttribute("gitlabcode")!=null?req.getSession().getAttribute("gitlabcode").toString():"";
		int userId = (Integer)req.getSession().getAttribute("userId");
		int type = (Integer)(req.getSession().getAttribute("gitType")!=null?req.getSession().getAttribute("gitType"):-1);
		switch (oper) {
		case GET_GIT_LIST:
			GitlabManager manager = new GitlabManager(type);
			manager.setAccessCode(oauth_token);
			try {
				JSONArray list =  manager.getProjectList();
				PrintWriter writer = resp.getWriter();
				writer.print(list);
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case CHOOSE_PROJ:
			String dataStr = type == GitlabManager.GITLAB?req.getParameter("proj_id"):req.getParameter("proj_name");
			if(dataStr!=null&&!dataStr.equals("")){
				manager = new GitlabManager(type);
				manager.setAccessCode(oauth_token);
				if(type == GitlabManager.GITLAB){
					try {
						JSONObject proj = manager.getProject(Integer.parseInt(dataStr));
						req.setAttribute("proj", proj);
						req.setAttribute("proj_id", proj.getInt("id"));
						req.setAttribute("proj_name", proj.getString("name"));
						req.setAttribute("proj_path", proj.getString("http_url_to_repo"));
						req.setAttribute("git_type", type);
						getServletContext().getRequestDispatcher("/proj_view.jsp").forward(req, resp);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(type == GitlabManager.GITHUB){
					try {
						JSONObject proj = manager.getProject(dataStr);
						req.setAttribute("proj", proj);
						req.setAttribute("proj_id", proj.getInt("id"));
						req.setAttribute("proj_name", proj.getString("name"));
						req.setAttribute("proj_path", proj.getString("clone_url"));
						req.setAttribute("git_type", type);
						getServletContext().getRequestDispatcher("/proj_view.jsp").forward(req, resp);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case GET_BRANCHES:
			int gitType = Integer.parseInt(req.getParameter("gitType"));
			manager = new GitlabManager(gitType);
			manager.setAccessCode(oauth_token);
			if(gitType == GitlabManager.GITLAB){
				int proj_id = Integer.parseInt(req.getParameter("id"));
				try {
					JSONArray branches = manager.getProjectBranches(proj_id);
					PrintWriter writer = resp.getWriter();
					writer.print(branches);
					writer.flush();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(gitType == GitlabManager.GITHUB){
				String proj_name = req.getParameter("name");
				try {
					JSONArray branches = manager.getProjectBranches(proj_name);
					PrintWriter writer = resp.getWriter();
					writer.print(branches);
					writer.flush();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case CREATE_DOCKER:
			int proj_id = Integer.parseInt(req.getParameter("proj_id"));
			String proj_name = req.getParameter("proj_name");
			String proj_desc = req.getParameter("proj_desc");
			String proj_repo_src = req.getParameter("proj_repo_src");
			String proj_repo_branch = req.getParameter("proj_repo_branch");
			String proj_con_num = req.getParameter("proj_con_num");
			gitType = Integer.parseInt(req.getParameter("git_type"));
			ServiceUtil serviceUtil = new ServiceUtil();
			try {
				int port = new CommonUtil().getNewPort();
				serviceUtil.createNewService(userId, proj_name, proj_desc,proj_id, proj_repo_src, proj_repo_branch,proj_con_num,port,gitType);
				JenkinsBuild jb = new JenkinsBuild(proj_name, proj_repo_src, oauth_token, "*/"+proj_repo_branch, port);
				jb.jenkinsBuild();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				getServletContext().getRequestDispatcher("/proj_lists.jsp").forward(req, resp);
			}
			break;
		case GET_LIST:
			serviceUtil = new ServiceUtil();
			try {
				JSONArray arr = serviceUtil.getUserServiceList(userId);
				PrintWriter writer = resp.getWriter();
				writer.print(arr);
				writer.flush();
				writer.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case OPER_PROJECT:
			getServletContext().getRequestDispatcher("/proj_operation.jsp").forward(req, resp);
			
			break;
		case PROJECT_INFO:
			proj_id = Integer.parseInt(req.getParameter("proj_id"));
			serviceUtil = new ServiceUtil();
			JSONObject info;
			try {
				info = serviceUtil.getProjectInfo(proj_id);
				gitType = info.getInt("git_type");
				manager = new GitlabManager(gitType);
				manager.setAccessCode(oauth_token);
				if(gitType == GitlabManager.GITLAB){
					JSONObject branch = manager.getProjectBranch(info.getInt("service_git_id"), info.getString("service_git_branch"));
					info.put("branch", branch);
				}
				else if(gitType == GitlabManager.GITHUB){
					JSONObject branch = manager.getProjectBranch(info.getString("service_name"),info.getString("service_git_branch"));
					info.put("branch", branch);
				}
				PrintWriter writer = resp.getWriter();
				writer.print(info);
				writer.flush();
				writer.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case PROJECT_OPER:
			proj_id = Integer.parseInt(req.getParameter("proj_id"));
			serviceUtil = new ServiceUtil();
			try {
				info = serviceUtil.getProjectInfo(proj_id);
				Docker docker = new Docker(info.getString("service_name"));
				String _type = req.getParameter("type");
				if(_type.equals("pause")){
					docker.pause();
				}
				else if(_type.equals("delete")){
					docker.delete();
				}
				PrintWriter writer = resp.getWriter();
				writer.print(0);
				writer.flush();
				writer.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
