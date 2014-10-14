<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">  
  <meta name="apple-mobile-web-app-capable" content="yes">  
  <meta name="apple-mobile-web-app-status-bar-style" content="black">  
  <title>乐培生师生平台</title>
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css/logout.css">
</head>
<body>
	<div class="main">
		<div class="left">
			<img src="img/pagelogo.jpg" class="logo">
			<ul>
				<li><a href="#" data-change-page="html/classRoom.html">我的教室</a></li>
				<li><a href="#" data-change-page="html/learn.html">我的学习</a></li>
				<li><a href="#" data-change-page="html/record.html">我的记录</a></li>
				<li><a href="#" data-change-page="html/information.html">用户资料</a></li>
				<li><a href="#" data-change-page="html/logout.html">退出系统</a></li>
			</ul>
		</div>
		<div class="right">
			<div class="content">
				<div class="title">
					<span class="title-text">所在位置: <span id="location"></span></span>
					<i id="fullIcon" class="icon icon-full"></i>
				</div>
				<div class="outerPage scroll"></div>
			</div>
		</div>
	</div>
	<div class="footer">
		<img src="img/footerbg.png" class="footer-img"/>
	</div>
	<script src="js/lib/fastclick.js"></script>
	<script>
		window.addEventListener('load', function () {
			FastClick.attach(document.body);
		}, false);
	</script>
	<script data-main="js/main" src="js/lib/require.js"></script>
</body>
</html>