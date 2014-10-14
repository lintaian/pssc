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
	<link rel="shortcut icon" href="img/favicon.ico">
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
		<div class="nameList">
			<a class="n_01" data-value="1"></a>
			<a class="n_02" data-value="2"></a>
			<a class="n_03" data-value="3"></a>
			<a class="n_04" data-value="4"></a>
			<a class="n_05" data-value="5"></a>
			<a class="n_06" data-value="6"></a>
			<a class="n_07" data-value="7"></a>
			<a class="n_08" data-value="8"></a>
			<a class="n_09" data-value="9"></a>
			<a class="n_close" data-close></a>
			<a class="n_00" data-value="0"></a>
			<a class="n_back" data-back></a>
		</div>
		<div class="pwdList">
			<a class="n_1" data-value="1"></a>
			<a class="n_2" data-value="2"></a>
			<a class="n_3" data-value="3"></a>
			<a class="n_4" data-value="4"></a>
			<a class="n_5" data-value="5"></a>
			<a class="n_6" data-value="6"></a>
			<a class="n_7" data-value="7"></a>
			<a class="n_8" data-value="8"></a>
			<a class="n_9" data-value="9"></a>
			<a class="n_close" data-close></a>
			<a class="n_0" data-value="0"></a>
			<a class="n_back" data-back></a>
		</div>
		<div id="msg" class="hide">
			<span class="text"></span>
			<a class="close">【关闭】</a>
		</div>
		<div id="cover" class="hide"></div>
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
	<script data-main="js/login" src="js/lib/require.js"></script>
</body>
</html>