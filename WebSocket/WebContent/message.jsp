<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>文件上传成功</title>

<script type="text/javascript" src="jquery-2.1.3.js"></script>
<link rel="stylesheet" type="text/css" href="css.css">
<link rel="stylesheet" type="text/css" href="progressbar.css">
<script type="text/javascript">
	$(document).ready(function() {
		setInterval("getCurrent()", 1000);
	});
	function getCurrent() {
		$.ajax({
			type : 'POST',
			url : '/WebSocket/dockerDeploy',
			dataType : 'text',
			success : function(response, status) {
				SetProgress(response);
			}
		});
	};
	
	function SetProgress(progress) {
		if (progress) {
			$("#loading> div")
					.css("width", String(progress) + "%"); //控制#loading div宽度 
			$("#loading > div").html(String(progress) + "%"); //显示百分比 
		}
	}
</script>
</head>
<body>
	<p>${message}</p>
	<div class="ins_num">${ins_num }</div>
	<p id="current">${current }</p>
	<div class="wrapper">
		<span>耗时: ${time}s</span>
	</div>
	<div id="center">
		<div id="message"></div>
		<div id="loading">
			<div></div>
		</div>
	</div>

</body>
</html>