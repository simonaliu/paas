<%@page import="org.json.JSONArray"%>
<%@page import="com.dbutil.ServiceUtil"%>
<%@page import="org.json.JSONObject"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="html/scripts/jquery-2.1.3.js"></script>
<script type="text/javascript" src="html/scripts/functions.js"></script>

<script type="text/javascript">
	
</script>
<title>登录成功</title>
</head>
<body>
	<%
		if (session.getAttribute("userId") != null) {
			int userId = (Integer) session.getAttribute("userId");
			ServiceUtil serviceDb = new ServiceUtil();
			JSONArray arr = serviceDb.getUserServiceList(userId);
			if (arr != null && arr.length() > 0) {
				response.sendRedirect("/WebSocket/proj_lists.jsp");
			} else {
				response.sendRedirect("/WebSocket/no_proj.jsp");
			}
		}
	%>
</body>
</html>