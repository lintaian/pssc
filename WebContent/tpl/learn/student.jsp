<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="learn" class="studentList">
	<c:forEach items="${obj }" var="o">
		<div class="item ${o._id==loginUser._id ? 'me clickable' : '' }">
			<img src="${o.photo == '' ? (o.sex==1 ? 'photo/girl.jpg' : 'photo/boy.jpg') : o.photo}" 
				class="${o.login_status==1 ? '' : 'cover' }"/>
			<div class="name ${o._id==loginUser._id ? 'me' : '' } ${o.sex==1 ? 'girl' : 'boy' }">${o.name }</div>
			<div class="icon_face laugh face"></div>
		</div>
	</c:forEach>
</div>