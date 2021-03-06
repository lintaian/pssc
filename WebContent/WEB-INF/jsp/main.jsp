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
  <title>泡桐小学(天府校区)云平台</title>
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
					<div class="hide currentName">(${loginUser.name })</div>
					<i id="fullIcon" class="icon icon-full"></i>
				</div>
				<div class="outerPage scroll"></div>
				<div id="contentModal"></div>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="footer-text">技术支持: 成都乐培晟科技有限公司</div>
		<img src="img/footerbg.png" class="footer-img"/>
	</div>
	<div id="msg" class="hide">
		<div class="msgTitle">
			<div class="titleText">提示信息</div>
			<div class="close"></div>
		</div>
		<div class="msgContent">密码更新成功!</div>
	</div>
	<div id="imageViewBg" class="hide"></div>
	<div id="imageView" class="hide">
		<div class="iv-close"></div>
		<span class="iv-zoom-scale"></span>
		<div class="iv-img scroll">
			<img src="photo/q1.png">
		</div>
	</div>
	<div id="modal" class="hide"></div>
	<div id="modal2" class="hide"></div>
	<div id="loading" class="hide">
		<img src="img/loader.gif">
		<div class="text"></div>
		<div class="progress"></div>
	</div>
	<div id="alert" class="hide">
		<div class="text"></div>
		<div class="button">
			<span class="btn">确定</span>
		</div>
	</div>
	<div id="preemptive_success" class="hide">
		<div class="text"></div>
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