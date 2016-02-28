package com.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostTest extends HttpServlet{
	
	private static int i = 1;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(req);
		
		i += 1;
		String str = i + "";
		PrintWriter writer = resp.getWriter();
		writer.print(str);
		writer.flush();
		writer.close();
	}
}
