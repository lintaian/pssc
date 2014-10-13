<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<title>乐培生师生平台</title>
	<script src="js/lib/jquery.js"></script>
</head>

<body >
	<br/><br/><br/><br/><br/><br/><br/>
	<input type="button" value="login">
	<script>
		$(function() {
			$('input').click(function() {
				$.ajax({
					url: 'login',
					type: 'post',
					contentType: 'application/json',
					dataType: 'json',
					data: JSON.stringify({
						'username': 'lta',
						'password': '123'
					}),
					success: function(data) {
						if (data.status) {
							window.location.href="main";
						}
					}
				});
			});
		});
	</script>
</body>
</html>