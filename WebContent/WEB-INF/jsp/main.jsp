<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
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
  <link rel="stylesheet" type="text/css" href="css/classRoom.css">
  <link rel="stylesheet" type="text/css" href="css/record.css">
  <link rel="stylesheet" type="text/css" href="css/info.css">
</head>
<body>
	<div class="main">
		<div class="left">
			<img src="img/pagelogo.jpg" class="logo">
			<ul>
				<li><a href="#" data-change-page="tpl/classRoom.html">我的教室</a></li>
				<li><a href="#" data-change-page="tpl/learn.html">我的学习</a></li>
				<li><a href="#" data-change-page="record">我的记录</a></li>
				<li><a href="#" data-change-page="user/info">用户资料</a></li>
				<li><a href="#" data-change-page="tpl/logout.html">退出系统</a></li>
			</ul>
		</div>
		<div class="right">
			<div class="content">
				<div class="title">
					<span class="title-text">所在位置: <span id="location"></span></span>
					<i id="fullIcon" class="icon icon-full"></i>
				</div>
				<div class="outerPage scroll"></div>
				<div id="contentModal"></div>
			</div>
		</div>
	</div>
	<div class="footer">
		<img src="img/footerbg.png" class="footer-img"/>
	</div>
	<div id="msg" class="hide">
		<div class="msgTitle">
			<div class="titleText">提示信息</div>
			<div class="close"></div>
		</div>
		<div class="msgContent">密码更新成功!</div>
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