<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>创建服务</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link href="html/css/jquery-ui-themes.css" type="text/css"
	rel="stylesheet" />
<link href="html/css/styles.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="html/scripts/jquery-2.1.3.js"></script>
<script type="text/javascript" src="html/scripts/functions.js"></script>

<script type="text/javascript">
	<%
	String username = "";
	if(session.getAttribute("userName")!=null){
		username = session.getAttribute("userName").toString();
	}
	%>
	$(document).ready(function() {
		var username = <%="\""+username+"\""%>
		console.log(username)
		$("a#login").text(username);
	});
</script>
</head>
<body>
	<div id="base">


		<div id="nav">

			<div id="nav_content">

				<div id="nav_logo">
					<img class="logo_png" class="img" src="html/images/logo.png" />
				</div>

				<ul class="nav">

					<li><a href="/WebSocket/proj_choose.jsp" target="">选择服务</a></li>

					<li><a href="/WebSocket/proj_lists.jsp" target="_self">运行服务</a></li>

					<li class="nav_login"><a id = "login" href="" target="_self">登录</a></li>

				</ul>

			</div>

		</div>

		<div id="content">

			<div id="content_title">
				<span class="content_title_style">服务列表</span>
			</div>

			<div id="content_line"></div>

			<div id="no_proj">

				<p>您还没有创建服务</p>
				<a href="/WebSocket/proj_choose.jsp">
				<input type="button" value="+ 创建服务" onclick="">
				</a>

			</div>

		</div>
		<div id="bottom">

			<div id="bottom_words">
				<p>版权 @2016 - 中国科技网CSTNET</p>
				<p>联系我们：liulinyu@cstnet.cn</p>
			</div>
		</div>


	</div>
</body>
</html>
