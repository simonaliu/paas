package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONStringer;

import com.dbutil.UserUtil;

public class LoginServlet extends HttpServlet {
	private static final int LOGIN_OAUTH = 1;
	private static final int REGISTER = 2;
	private static final int LOGIN = 3;
	private static final int USERNAME_VERIFY = 4;
	private static final int DO_REGISTER = 5;
	private static final int RE_LOGIN = 6;
	
	

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			try {
				process(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			try {
				process(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String stayTime = req.getParameter("stayTime");
		if (stayTime != null) {
			System.out.println(stayTime);
		}
	}

	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException, SQLException, ClassNotFoundException, JSONException {
		try {
			int oper = Integer.parseInt(req.getParameter("oper"));

			UserUtil userDB;
			PrintWriter writer;
			JSONStringer response;
			switch (oper) {
			case LOGIN_OAUTH:
				
				break;
			case REGISTER:
				//getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
				resp.sendRedirect("/WebSocket/register.jsp");
				break;
			case LOGIN:
				String username = req.getParameter("username");
				String userpsw = req.getParameter("userpsw");
				if(username==null||username.equals("")||userpsw==null||userpsw.equals("")){
					req.setAttribute("errcode", 1);
					getServletContext().getRequestDispatcher("/login_err.jsp").forward(req, resp);
					return;
				}
				userDB = new UserUtil();
				int userid = userDB.loginCheck(username, userpsw);
				if(userid!=-1){
					HttpSession session = req.getSession();
					session.setMaxInactiveInterval(30);
					session.setAttribute("userName", username);
					session.setAttribute("userId", new Integer(userid));
					req.setAttribute("errcode", 0);
					getServletContext().getRequestDispatcher("/login_success.jsp").forward(req, resp);
				}
				else{
					req.setAttribute("errcode", 2);
					getServletContext().getRequestDispatcher("/login_err.jsp").forward(req, resp);
				}
				break;
			case USERNAME_VERIFY:
				username = req.getParameter("username");
				userDB = new UserUtil();
				boolean hasName = userDB.nameCheck(username);
				response = new JSONStringer();
				response.object();
				response.key("hasName").value(hasName);
				response.endObject();
				writer=resp.getWriter();
				writer.print(response);
				writer.flush();
				writer.close();
				break;
			case DO_REGISTER:
				username = req.getParameter("username");
				userpsw = req.getParameter("userpsw");
				String userpsw2 = req.getParameter("userpsw2");
				userDB = new UserUtil();
				if(username==null||username.equals("")||userpsw==null||userpsw.equals("")){
					req.setAttribute("errcode", 3);
					getServletContext().getRequestDispatcher("/login_err.jsp").forward(req, resp);
					return;
				}
				if(userpsw.equals(userpsw2) == false){
					req.setAttribute("errcode", 4);
					getServletContext().getRequestDispatcher("/login_err.jsp").forward(req, resp);
					return;
				}
				if(userDB.nameCheck(username)){
					req.setAttribute("errcode", 5);
					getServletContext().getRequestDispatcher("/login_err.jsp").forward(req, resp);
					return;
				}
				boolean result = userDB.registerNewUser(username, userpsw);
				if(result){
					userid = userDB.loginCheck(username, userpsw);
					HttpSession session = req.getSession();
					session.setAttribute("userName", username);
					session.setAttribute("userId", new Integer(userid));
					session.setMaxInactiveInterval(30);
					req.setAttribute("errcode", 0);
					getServletContext().getRequestDispatcher("/login_success.jsp").forward(req, resp);
				}
				else{
					req.setAttribute("errcode", 6);
					getServletContext().getRequestDispatcher("/login_err.jsp").forward(req, resp);
				}
				break;
			case RE_LOGIN:
				getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
				break;
			default:
				break;
			}
			//

		} catch (NumberFormatException e) {

		}
	}
}
