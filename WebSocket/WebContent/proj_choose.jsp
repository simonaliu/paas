<%@page import="org.json.JSONArray"%>
<%@page language="java" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>服务列表</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="html/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="html/css/styles.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="html/scripts/jquery-2.1.3.js"></script>
<script type="text/javascript" src="html/scripts/functions.js"></script>

    
	<script type="text/javascript">
<%
String username = "";
if(session.getAttribute("userName")!=null){
	username = session.getAttribute("userName").toString();
}
  String oauth_token = "";
  if(session.getAttribute("gitlabcode")!=null){
    oauth_token = session.getAttribute("gitlabcode").toString();
  }
  int gitType = 0;
  if(session.getAttribute("gitType")!=null){
	  gitType = (Integer)session.getAttribute("gitType");
  }
%>
/*
        <div id="repo_lists">
          <a class="title" href=" ">simonaliu/crawl</a>
          <p class="desc">5 websites</p>
          <input type="button" value="选择">
          <div id="proj_line"></div>
        </div>
  */
  function getListHtml(data){
    var item = $("<div></div>").attr({
      id: 'repo_lists',
    })
    var title = $("<a></a>").attr({
        class: 'title',
        href:''
    }).text(data.name)
    var desc = $("<p></p>").attr({
      class: 'desc'
    }).text(data.description);
    var btn = $("<input></input>").attr({
      type: 'button',
      value: '选择'
    });
    var gitType = <%=gitType%>
    btn.click(function(event) {
    if(gitType==1){
    	 $.form("Service",
	        {
	          oper:2,
	          proj_id:data.id
	          //name:data.namespace.name,
	          //path:data.namespace.path,
	        },"POST").submit();
    }
    else if(gitType==2){
    	 $.form("Service",
	        {
	          oper:2,
	          proj_id:data.id,
	          proj_name:data.name
	          //name:data.namespace.name,
	          //path:data.namespace.path,
	        },"POST").submit();
    }
     
    });
    var line = $("<div></div>").attr({
      id: 'proj_line'
    }); 
    item.append(title).append(desc).append(btn).append(line)

    return item
/*
    .html(
      $("<a></a>").attr({
        class: 'title',
        href:''
      }).text(data.name)
    );
*/
  }
	$(document).ready(function() {
		var username = <%="\""+username+"\""%>
		console.log(username)
    var oauth_token = <%="\""+oauth_token+"\""%>
		$("a#login").text(username);
	    $("img.gitlab_png").click(function(event) {
	      $.form("Oauth",{type:2},"POST").submit();
	    });
	    $("img.github_png").click(function(event) {
	      $.form("Oauth",{type:1},"POST").submit();
	    });

    if(oauth_token!=""){
    	console.log(oauth_token)
      $.ajax({
        url: 'Service',
        type: 'POST',
        dataType: 'json',
        data: {oper: '1'},
      })
      .done(function(data) {
        if(data==-999){
          $.form("Login",
            {oper:6},"POST"
          ).submit();
          return
        }
        console.log(data)
        for(var i = 0;i<data.length;i++){
          $("div#repo_proj").append(getListHtml(data[i]))
        }
      })
      .fail(function() {
        console.log("error");
      })
    }
	});
	
	
	
	</script>

   
     </head>
  <body>
    <div id="base" >


      <div id="nav" > 

      <div id="nav_content" >

      <div id="nav_logo" >
        <img class="logo_png" src="html/images/logo.png"/>
      </div>
      
      <ul class="nav">

      <li>
      
          <a href="/WebSocket/proj_choose.jsp" target="_self">选择服务</a>
  
      </li>

      <li><a href="/WebSocket/proj_lists.jsp" target="_self">运行服务</a></li>

      

      <li class = "nav_login">

      <a id = "login" href="" target="_self">登录</a>
        
      </li>

      </ul>

      </div>

      </div>
      
      <div id="content">
        
        <div id="content_title" >
          <span class ="content_title_style">服务列表</span>
        </div>

        <div id="content_line" >

        </div>
  
        <div id="proj_cont">
          
      
        <div id="code_repo">

        <p>选择一个代码仓库</p>

        <img src="html/images/github.png" class="github_png">

        <img src="html/images/gitlab.png" class="gitlab_png">
          
        <a href="http://159.226.57.11" class="repo" target="_blank">还没有注册代码仓库？我们提供Gitlab代码仓库服务</a>  

        </div>

      <div id="repo_proj">
        
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
