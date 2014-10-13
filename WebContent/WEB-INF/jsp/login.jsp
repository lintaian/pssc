<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" type="text/css" href="css/login.css">
</head>

<body>
	<div class="main">
		<div class="clear"></div>
		<div class="left">
			<div class="name"></div>
			<div class="pwd"></div>
		</div>
		<div class="right"></div>
		<div class="nameList">
			<a class="n_01"></a>
			<a class="n_02"></a>
			<a class="n_03"></a>
			<a class="n_04"></a>
			<a class="n_05"></a>
			<a class="n_06"></a>
			<a class="n_07"></a>
			<a class="n_08"></a>
			<a class="n_09"></a>
			<a class="n_close"></a>
			<a class="n_00"></a>
			<a class="n_back"></a>
		</div>
		<div class="pwdList">
			<a class="n_1"></a>
			<a class="n_2"></a>
			<a class="n_3"></a>
			<a class="n_4"></a>
			<a class="n_5"></a>
			<a class="n_6"></a>
			<a class="n_7"></a>
			<a class="n_8"></a>
			<a class="n_9"></a>
			<a class="n_close"></a>
			<a class="n_0"></a>
			<a class="n_back"></a>
		</div>
	</div>
	<div class="footer">
		<img src="img/footerbg.png" class="footer-img"/>
	</div>
	<script data-main="js/login" src="js/lib/require.js"></script>
</body>
</html>