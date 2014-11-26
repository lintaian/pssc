<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
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
	<title>泡桐小学-翻转课堂学生信息管理</title>
	<link rel="shortcut icon" href="img/favicon.ico">
	<link rel="stylesheet" type="text/css" href="admin/css/main.css">
</head>

<body>
	<a href="admin/student/update" class="btn">添加</a>
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>姓名</th>
				<th>登录账号</th>
				<th>性别</th>
				<th>状态</th>
				<th>登陆状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${obj }" var="s">
			<tr>
				<td>${s.name }</td>
				<td>${s.login_name }</td>
				<td>${s.sex == 0 ? '男' : '女'}</td>
				<td>${s.status == 0 ? '未激活' : '激活' }</td>
				<td>${s.login_status == 0 ? '不在线' : '在线' }</td>
				<td><a href="admin/student/update?id=${s._id }">修改</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>