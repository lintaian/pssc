<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<table class="student" cellpadding="0" cellspacing="0">
		<tr>
			<td>姓名:</td>
			<td>
				<input type="hidden" name="id" value="${obj.student._id }" />
				<input type="text" name="name" value="${obj.student.name }" />
			</td>
		</tr>
		<tr>
			<td>登陆账号:</td>
			<td>
				<input type="text" name="login_name" value="${obj.student.login_name }" />
			</td>
		</tr>
		<tr>
			<td>班级:</td>
			<td>
				<select name="class_id">
					<c:forEach items="${obj.classes }" var="c">
						<c:if test="${obj.student.class_id == c._id }">
							<option value="${c._id }-${c.grade_id }" selected="selected">${c.year } ${c.name }</option>
						</c:if>
						<c:if test="${obj.student.class_id != c._id }">
							<option value="${c._id }-${c.grade_id }">${c.year } ${c.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>生日:</td>
			<td>
				<input type="date" name="birthday" 
					value='<fmt:formatDate value="${obj.student.birthday}"  pattern="yyyy-MM-dd"/>'/>
			</td>
		</tr>
		<tr>
			<td>性别:</td>
			<td>
				<select name="sex">
					<c:if test="${obj.student.sex != 1}">
						<option value="0" selected="selected">男</option>
						<option value="1">女</option>
					</c:if>
					<c:if test="${obj.student.sex == 1}">
						<option value="0">男</option>
						<option value="1" selected="selected">女</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td>状态:</td>
			<td>
				<select name="status">
					<c:if test="${obj.student.status == 0}">
						<option value="1">激活</option>
						<option value="0" selected="selected">未激活</option>
					</c:if>
					<c:if test="${obj.student.status != 0}">
						<option value="1" selected="selected">激活</option>
						<option value="0">未激活</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td>登陆状态:</td>
			<td>
				<select name="login_status">
					<c:if test="${obj.student.login_status != 1}">
						<option value="1">在线</option>
						<option value="0" selected="selected">不在线</option>
					</c:if>
					<c:if test="${obj.student.login_status == 1}">
						<option value="1" selected="selected">在线</option>
						<option value="0">不在线</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td>密码:</td>
			<td>
				<input type="password" name="auth_code"/>
			</td>
		</tr>
		<tr>
			<td>头像:</td>
			<td>
				<img src="${obj.student.photo }" class="photo">
				<form id="fileUpload">
					<input type="file" name="file" id="photo"/>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="save">
				<span class="btn save">保存</span>
			</td>
		</tr>
	</table>
	<script src="js/lib/jquery.js"></script>
	<script src="admin/js/student.js"></script>
</body>
</html>