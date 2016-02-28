<%@page import="org.json.JSONObject"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="html/scripts/jquery-2.1.3.js"></script>
<script type="text/javascript" src="html/scripts/functions.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		var user_id =<%=session.getAttribute("userId")%>
		console.log(user_id);
	});
</script>
<title>登录成功</title>
</head>
<body>
	<%
		int errcode = Integer.parseInt(request.getAttribute("errcode")
				.toString());
		System.out.println(errcode);
		switch (errcode) {
		case 1:
		case 2:
			getServletContext().getRequestDispatcher("/login.jsp").forward(
					request, response);
			break;
		case 3:
		case 4:
		case 5:
		case 6:
			getServletContext().getRequestDispatcher("/register.jsp")
					.forward(request, response);
			break;
		}
	%>
</body>
</html>