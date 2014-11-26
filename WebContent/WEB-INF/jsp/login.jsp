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
	<title>泡桐小学-翻转课堂</title>
	<link rel="shortcut icon" href="img/favicon.ico">
	<link rel="stylesheet" type="text/css" href="css/login.css">
	<link rel="stylesheet" type="text/css" href="css/patternLock.css">
</head>

<body>
	<div class="main hide">
		<div class="clear"></div>
		<div class="left">
			<div class="name">
				<div class="nameText"></div>
			</div>
			<div class="pwd">
				<div class="pwdText"></div>
			</div>
		</div>
		<div class="right"></div>
		<div class="nameList hide">
			<a class="n_01" data-value="1" data-nodelay></a>
			<a class="n_02" data-value="2" data-nodelay></a>
			<a class="n_03" data-value="3" data-nodelay></a>
			<a class="n_04" data-value="4" data-nodelay></a>
			<a class="n_05" data-value="5" data-nodelay></a>
			<a class="n_06" data-value="6" data-nodelay></a>
			<a class="n_07" data-value="7"></a>
			<a class="n_08" data-value="8"></a>
			<a class="n_09" data-value="9"></a>
			<a class="n_close" data-close></a>
			<a class="n_00" data-value="0"></a>
			<a class="n_back" data-back></a>
		</div>
		<div class="pwdList hide">
			<div id="patternLock"></div>
		</div>
	</div>
	<div class="footer">
		<img src="img/footerbg.png" class="footer-img"/>
	</div>
	<div id="modal" class="hide"></div>
	<div id="alert" class="hide">
		<div class="text"></div>
		<div class="button">
			<span class="btn">确定</span>
		</div>
	</div>
	<script src="js/lib/fastclick.js"></script>
	<script>
		window.addEventListener('load', function () {
			FastClick.attach(document.body);
		}, false);
	</script>
	<script data-main="js/login" src="js/lib/require.js"></script>
</body>
</html>