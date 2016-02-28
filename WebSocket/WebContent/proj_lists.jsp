<%@page import="com.common.CommonSetting"%>
<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>运行服务</title>
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
	if(session.getAttribute("gitlabcode")==null){
		//session.setAttribute("now", "proj_list");
		//response.sendRedirect(CommonSetting.GITLAB_URL_OAUTH);
	}
%>
  /*
    <div id="proj_listview">
          <a class="title" href=" ">simonaliu/crawl</a> <input type="button"
            value="运行中" class="green"> <input type="button" value="操作">
          <a href="http://159.226.57.12:30000" class="request">访问服务</a>
          <p class="desc">5 websites</p>
          <div id="proj_line"></div>
        </div>
  */
  function getProjectItem(data){
    var item = $("<div></div>").attr({
      id: 'proj_listview',
    })
    var title = $("<a></a>").attr({
        class: 'title',
        href:''
    }).text(data.service_name)
    var desc = $("<p></p>").attr({
      class: 'desc'
    }).text(data.service_desc);
    var status = $("<input></input>").attr({
      type: 'button',
      value: '运行中',
      class:"green"
    });
    var btn = $("<input></input>").attr({
      type: 'button',
      value: '操作'
    });
    var visit = $("<a></a>").attr({
        class: 'request',
        href:'http://159.226.57.12:' + data.service_docker_port
    }).text('访问服务')
    btn.click(function(event) {
      $.form("Service",
        {
          oper:6,
          id:data.service_id,
          gitType:data.git_type
        },"POST").submit();
    });
    var line = $("<div></div>").attr({
      id: 'proj_line'
    }); 
    item.append(title).append(status).append(btn).append(visit).append(desc).append(line)

    return item
  }
  $(document).ready(function() {
    $("[name='oper']").hide();
    var username =<%="\"" + username + "\""%>
    console.log(username)
    $("a#login").text(username);
    $.ajax({
      url: 'Service',
      type: 'POST',
      dataType: 'json',
      data: {
        oper:'5'
      },
    })
    .done(function(data) {
      if(data==-999){
        $.form("Login",
          {oper:6},"POST"
        ).submit();
        return
      }
      for(var i = 0;i<data.length;i++){
          $("div#proj_cont").append(getProjectItem(data[i]))
        }
    })
    .fail(function(data) {
      console.log("error");
      console.log(data);
    })
    
  })
   </script>
</head>
<body>
	<div id="base">


		<div id="nav">

			<div id="nav_content">

				<div id="nav_logo">
					<img class="logo_png" src="html/images/logo.png" />
				</div>

				<ul class="nav">

					<li><a href="/WebSocket/proj_choose.jsp" target="_self">选择服务</a></li>

					<li><a href="" target="_self">运行服务</a></li>



					<li class="nav_login"><a id="login" href="" target="_self">登录</a>

					</li>

				</ul>

			</div>

		</div>

		<div id="content">

			<div id="content_title">
				<span class="content_title_style">服务列表</span>
			</div>

			<div id="content_line"></div>

			<div id="proj_cont">

				

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
