package com.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.common.CommonSetting;
import com.dbutil.ServiceUtil;
import com.git.GitlabManager;

@SuppressWarnings("serial")
public class OauthGitHubServlet extends HttpServlet {
	
	public static final int GITHUB_JUMP = 1;
	
	public static final int GITLAB_JUMP = 2;
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String code = req.getParameter("code");
		req.getSession().setAttribute("gitType", GitlabManager.GITHUB);
		if(code!=null){
			GitlabManager manager = new GitlabManager(GitlabManager.GITHUB);
			manager.setOauthCode(code);
			try {
				manager.getAccessToken();
				if(manager.isAccess()){
					req.getSession().setAttribute("gitlabcode", manager.getOauthToken());
					req.setAttribute("errcode", 0);
					if(req.getSession().getAttribute("now")!=null){
						getServletContext().getRequestDispatcher(String.format("/%s.jsp",req.getSession().getAttribute("now"))).forward(req, resp);
					}
					else{
						getServletContext().getRequestDispatcher("/proj_choose.jsp").forward(req, resp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(e.getClass()==JSONException.class){
					req.setAttribute("errcode", 1);
					if(req.getSession().getAttribute("now")!=null){
						getServletContext().getRequestDispatcher(String.format("/%s.jsp",req.getSession().getAttribute("now"))).forward(req, resp);
					}
					else{
						getServletContext().getRequestDispatcher("/proj_choose.jsp").forward(req, resp);
					}			
				}
			}
		}

		//System.out.println(arr.toString());
		
	}
}
