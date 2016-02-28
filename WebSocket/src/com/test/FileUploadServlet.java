package com.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
	 static int ins_num = 1;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		getServletContext().getRequestDispatcher("/message.jsp").forward(
				request, response);
	}
}
