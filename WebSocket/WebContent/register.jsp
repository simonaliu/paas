<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>注册</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link href="html/css/jquery-ui-themes.css" type="text/css"
	rel="stylesheet" />
<link href="html/css/styles.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="html/scripts/jquery-2.1.3.js"></script>
<script type="text/javascript" src="html/scripts/functions.js"></script>

<script type="text/javascript">
<%	
int errcode = 0;
if (request.getAttribute("errcode") != null) {
	errcode = Integer.parseInt(request.getAttribute("errcode").toString());
}
%>
      var hasName;
      $(document).ready(function() {
        $("input[name = 'oper']").hide();
        var errcode = <%=errcode%>
		console.log(errcode);
		if (errcode == 3) {
			alert("用户名或密码不可为空");
		} else if (errcode == 4) {
			alert("两次输出的密码不相符");
		} else if (errcode == 5) {
			alert("用户名已存在");
		} else if (errcode == 6) {
			alert("用户名已存在");
		}
		$("input[name = 'oper']").hide();
		$("input[name='username']").focusout(function(event) {
			var username = $("input[name='username']").val()
			if (username == "") {
				return
			}
			;
			$.ajax({
				url : 'Register',
				type : 'POST',
				dataType : 'json',
				data : {
					oper : 4,
					username : username
				},
			}).done(function(data) {
				hasName = data.hasName
				if (hasName) {
					alert("用户名已存在")
				}
			}).fail(function() {
				console.log("error");
			})
		});
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

					<li><a href="/WebSocket/proj_choose.jsp" target="_self">选择服务</a></li>

					<li><a href="/WebSocket/proj_lists.jsp" target="_self">运行服务</a></li>



					<li class="nav_login"><a href="" target="_self">登录</a></li>

				</ul>

			</div>

		</div>

		<div id="content">

			<div id="content_title">
				<span class="content_title_style">注册</span>
			</div>

			<div id="content_line"></div>


			<form class="content_text" action="Register" method="post">
				<input type="text" name="oper" value="5">
				<div id="content_text1">
					<span class="content_text_sytle">用户名</span> <input type="text"
						name="username">
				</div>

				<div id="content_text1">
					<span class="content_text_sytle">密码</span> <input type="password"
						name="userpsw">
				</div>

				<div id="content_text1">
					<span class="content_text_sytle">密码验证</span> <input type="password"
						name="userpsw2">
				</div>

				<div id="register">

					<input id="login" type="submit" value="注册">

				</div>

			</form>

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
