<%@page import="org.json.JSONObject"%>
<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>服务配置</title>
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
	int proj_id = 0;
	if(request.getAttribute("proj_id")!=null){
		proj_id = (Integer)request.getAttribute("proj_id");
	}
	String proj_name = "";
	if(request.getAttribute("proj_name")!=null){
		proj_name = request.getAttribute("proj_name").toString();
	}
	int git_type = 0;
	if(request.getAttribute("git_type")!=null){
		git_type = (Integer)request.getAttribute("git_type");
	}
%>
	var gitType = 0
	$(document).ready(function() {
		$("[name='oper']").hide();
		var username =<%="\"" + username + "\""%>
		console.log(username)
		gitType = <%=git_type%>
		$("a#login").text(username);
		if(gitType ==1){
			var proj_id = <%=proj_id%>;
			if(proj_id>0){
				$.ajax({
					url: 'Service',
					type: 'POST',
					dataType: 'json',
					data: {
						oper: '3',
						id:proj_id,
						gitType:gitType
					},
				})
				.done(function(data) {
					if(data==-999){
			          $.form("Login",
			            {oper:6},"POST"
			          ).submit();
			          return
			        }
					var selector = $("select[name='proj_repo_branch']")
					for(var i = 0;i<data.length;i++){
						var option = $("<option>").val(data[i].name).text(data[i].name)
						selector.append(option)
					}
				})
				.fail(function() {
					console.log("error");
				});
			}
		}
		else if(gitType==2){
			var proj_name = <%="\""+proj_name+"\""%>;
			if(proj_name){
				$.ajax({
					url: 'Service',
					type: 'POST',
					dataType: 'json',
					data: {
						oper: '3',
						name:proj_name,
						gitType:gitType
					},
				})
				.done(function(data) {
					if(data==-999){
			          $.form("Login",
			            {oper:6},"POST"
			          ).submit();
			          return
			        }
					var selector = $("select[name='proj_repo_branch']")
					for(var i = 0;i<data.length;i++){
						var option = $("<option>").val(data[i].name).text(data[i].name)
						selector.append(option)
					}
				})
				.fail(function() {
					console.log("error");
				});
			}
		}
		$("form.content_text").submit(function(event) {
			console.log(event)
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



					<li class="nav_login"><a id="login" href="" target="_self">登录</a></li>

				</ul>

			</div>

		</div>

		<div id="content">

			<div id="content_title">
				<span class="content_title_style">服务配置</span>
			</div>

			<div id="content_line"></div>


			<form class="content_text" action="Service" method="post">
				<input type="hidden" name = "oper" value="4">
				<input type="hidden" name="git_type" value=${git_type}>
				<input type="hidden" name="proj_id" value=${proj_id}> 
				<div id="content_text1">
					<span class="content_text_sytle">服务名称</span> 
					<input type="text"
						name="proj_name" value=${proj_name} readonly="readonly">
				</div>

				<div id="content_text1">
					<span class="content_text_sytle">简单描述</span> <input type="text"
						name="proj_desc">
				</div>

				<div id="content_text1">
					<span class="content_text_sytle">代码仓库</span> <input type="text"
						name="proj_repo_src" value=${proj_path}
						readonly="readonly">
				</div>

				<div id="content_text1">
					<span class="content_text_sytle">代码分支</span> <select type="text"
						name="proj_repo_branch">

						</select>
				</div>

				<div id="content_text1">
					<span class="content_text_sytle">容器数量</span> <input type="text"
						name="proj_con_num">
				</div>

				<div id="proj_cre_btn">

					<input type="submit" value="创建服务并运行">

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
