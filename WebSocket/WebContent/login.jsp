<%@page language="java" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>登录</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="html/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="html/css/styles.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="html/scripts/jquery-2.1.3.js"></script>
<script type="text/javascript" src="html/scripts/functions.js"></script>

    <script type="text/javascript">
	<%
		int errcode = 0;
		if(request.getAttribute("errcode")!=null){
			errcode = Integer.parseInt(request.getAttribute("errcode").toString());
		}
		if(session.getAttribute("userId")!=null){
			response.sendRedirect("/WebSocket/login_success.jsp");
		}
	%>
      $(document).ready(function() {
        $("input[name = 'oper']").hide();
        var errcode = <%=errcode%>
        if(errcode==1){
        	alert("用户名或密码不可为空");
        }
        else if(errcode==2){
        	alert("用户名或密码不正确");
        }
      });
    
    </script>

   
     </head>
  <body>
    <div id="base" >


      <div id="nav" > 

      <div id="nav_content" >

      <div id="nav_logo" >
        <img class="logo_png" class="img" src="html/images/logo.png"/>
      </div>
      
      <ul class="nav">

      <li>
      
          <a href="/WebSocket/proj_choose.jsp" target="">选择服务</a>
  
      </li>

      <li><a href="/WebSocket/proj_lists.jsp" target="_self">运行服务</a></li>

      

      <li class = "nav_login">

      <a href="" target="_self">登录</a>
        
      </li>

      </ul>

      </div>

      </div>
      
      <div id="content">
        
        <div id="content_title" >
          <span class ="content_title_style">登录</span>
        </div>

        <div id="content_line" >

        </div>


      <form class="content_text" action="Login" method="post">
      <input type="text" name="oper" value="3">
       <div id="content_text1" >
          <span class="content_text_sytle">用户名</span>
          <input type="text" name="username" >
      </div>

      <div id="content_text1" >
          <span class="content_text_sytle">密码</span>
          <input type="password" name="userpsw" >
      </div>

      <div id="content_pwd_forget">
          <a href="#" target="">忘记密码？</a>
          <a href="/WebSocket/register.jsp" >注册新账户</a>
      </div>

      <div id="register" >
        
        <input id="login" type="submit" value="登录">

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
