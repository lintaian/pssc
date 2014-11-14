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
  <!-- <meta http-equiv="pragma" content="no-cache"> 
  <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
  <meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT"> -->
  <title>泡桐小学-翻转课堂</title>
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <link rel="stylesheet" type="text/css" href="css/patternLock.css">
  <link rel="stylesheet" type="text/css" href="css/logout.css">
  <link rel="stylesheet" type="text/css" href="css/classRoom.css">
  <link rel="stylesheet" type="text/css" href="css/record.css">
  <link rel="stylesheet" type="text/css" href="css/info.css">
  <link rel="stylesheet" type="text/css" href="css/learn.css">
  <link rel="stylesheet" type="text/css" href="css/video.css">
  <link rel="stylesheet" type="text/css" href="css/exercise.css">
  <link rel="stylesheet" type="text/css" href="css/canvas.css">
  <link rel="stylesheet" type="text/css" href="css/picture.css">
  <link rel="stylesheet" type="text/css" href="css/preemptive.css">
  <link rel="stylesheet" type="text/css" href="css/workbook.css">
  <!-- <script src="https://getfirebug.com/firebug-lite.js"></script> -->
</head>
<body>
	<div class="main">
		<div class="left">
			<img src="img/pagelogo.jpg" class="logo">
			<ul>
				<li><a href="#" data-change-page="teach">我的教室</a></li>
				<li><a href="#" data-change-page="learn">我的学习</a></li>
				<li><a href="#" data-change-page="workbook?init=true">我的作业</a></li>
				<li><a href="#" data-change-page="record">我的记录</a></li>
				<li><a href="#" data-change-page="user/info">用户资料</a></li>
				<li><a href="#" data-change-page="user/logout">退出系统</a></li>
			</ul>
		</div>
		<div class="right">
			<div class="content">
				<div class="title">
					<div>所在位置: </div>
					<div id="location"></div>
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