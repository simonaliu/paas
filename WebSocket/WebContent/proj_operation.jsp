<%@page import="com.git.GitlabManager"%>
<%@page import="com.common.CommonSetting"%>
<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>服务操作</title>
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
			if (session.getAttribute("userName") != null) {
				username = session.getAttribute("userName").toString();
			}
			int proj_id = 0;
			if (request.getParameter("id") != null) {
				proj_id = Integer.parseInt(request.getParameter("id"));
			}
			else{
				if(session.getAttribute("proj_id")!=null){
					proj_id = (Integer)session.getAttribute("proj_id");
				}
			}
			int gitType = 0;
			if(request.getParameter("gitType")!=null){
				gitType = Integer.parseInt(request.getParameter("gitType"));
			}
			else{
				if(session.getAttribute("gitType")!=null){
					gitType = (Integer)session.getAttribute("gitType");
				}
			}
			if(session.getAttribute("gitlabcode")==null){
				session.setAttribute("now", "proj_operation");
				session.setAttribute("proj_id", proj_id);
				session.setAttribute("gitType", gitType);
				if(gitType==GitlabManager.GITLAB){
					response.sendRedirect(CommonSetting.GITLAB_URL_OAUTH);
				}
				else if(gitType == GitlabManager.GITHUB){
					response.sendRedirect(CommonSetting.GITHUB_URL_OAUTH);
				}
			}
			%>
			
      var proj_data;
    $(document).ready(function() {
      var username =<%="\"" + username + "\""%>
    console.log(username)
    $("a#login").text(username);

      $.ajax({
        url: 'Service',
        type: 'POST',
        dataType: 'json',
        data: {
          oper: '7',
          proj_id:<%=proj_id%>
        },
      })
      .done(function(data) {
        if(data==-999){
          $.form("Login",
            {oper:6},"POST"
          ).submit();
          return
        }
        proj_data = data
		var gitType = <%=gitType%>
        
        if(gitType==1){
	        var info = $("div#proj_left")
	        info.append($("<p></p>").text('创建时间：' + data.branch.commit.authored_date))
	        info.append($("<p></p>").text('更新时间：' + data.branch.commit.committed_date))
	        info.append($("<p></p>").text('代码仓库：' + data.service_git_url))
	        info.append($("<p></p>").text('代码分支：' + data.service_git_branch))
	        $("span#proj_name").text(data.service_name)
	        $("p.desc").text(data.service_desc)
	        $("a#proj_url").attr('href', 'http://159.226.57.12:'+data.service_docker_port);
	        $("a#proj_url").text('http://159.226.57.12:'+data.service_docker_port);
        }
        else if(gitType == 2){
        	var info = $("div#proj_left")
	        info.append($("<p></p>").text('创建时间：' + data.branch.commit.commit.author.date))
	        info.append($("<p></p>").text('更新时间：' + data.branch.commit.commit.committer.date))
	        info.append($("<p></p>").text('代码仓库：' + data.service_git_url))
	        info.append($("<p></p>").text('代码分支：' + data.service_git_branch))
	        $("span#proj_name").text(data.service_name)
	        $("p.desc").text(data.service_desc)
	        $("a#proj_url").attr('href', 'http://159.226.57.12:'+data.service_docker_port);
	        $("a#proj_url").text('http://159.226.57.12:'+data.service_docker_port); 	
        }
      })
      .fail(function() {
        console.log("error");
      })
      
      $("input#pause").click(function(event) {
        if(proj_data!=null){
           $.ajax({
              url: 'Service',
              type: 'POST',
              dataType: 'json',
              data: {
                oper: '8',
                proj_id:<%=proj_id%>,
                type:"pause"
              },
            })
           .done(function(data) {
             console.log("success");
           })
           .fail(function() {
             console.log("error");
           })
         }
      });
      $("input#delete").click(function(event) {
        if(proj_data!=null){
           $.ajax({
              url: 'Service',
              type: 'POST',
              dataType: 'json',
              data: {
                oper: '8',
                proj_id:<%=proj_id%>,
                type:"delete"
              },
            })
           .done(function(data) {
             console.log("success");
           })
           .fail(function() {
             console.log("error");
           })
         }
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

					<li><a href="/WebSocket/proj_choose.jsp" target="_self">选择服务</a>

					</li>

					<li><a href="/WebSocket/proj_lists.jsp" target="_self">运行服务</a></li>



					<li class="nav_login"><a id="login" href="" target="_self">登录</a></li>

				</ul>

			</div>

		</div>

		<div id="content">

			<div id="content_title">
				<span class="content_title_style">服务操作</span>
			</div>

			<div id="content_line"></div>

			<div id="repo_proj">

				<div id="proj_tit">
					<img src="html/images/file.png"> <span id="proj_name">simonaliu
						> crawl </span> <input type="button" value="运行中" class="green">
					<p class="desc">简单描述部分</p>

					<div id="proj_left"></div>

					<div id="proj_right">
						<input id="pause" type="button" value="暂停服务"> 
            <input id="delete" type="button"
							value="删除服务">
					</div>

					<div id="clear"></div>

					<div id="proj_bottom">
						<p>
							服务访问地址：<a id="proj_url" href="http://159.226.57.12:30000"
								target="_blank">http://159.226.57.12:30000</a>
						</p>
					</div>

				</div>

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
