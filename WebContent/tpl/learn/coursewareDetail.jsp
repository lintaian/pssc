<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="coursewareDetail">
	<div class="item_title">视频</div>
	<div class="videoList">
		<c:forEach items="${obj.videos }" var="v">
			<div class="item" data-id="${v._id }" data-name="${v.title }">
				<img src="${v.photo == '' ? 'photo/courseware.jpg' : v.photo}" />
				<div class="name">${v.title }</div>
				<div class="face icon_face laugh"></div>
				<div class="operate c_icon c_icon_video"></div>
			</div>
		</c:forEach>
	</div>
	<div class="item_title">习题</div>
	<div class="exerciseList">
		<c:forEach items="${obj.exercises }" var="e">
			<div class="item" data-id="${e._id }" data-name="${e.title }">
				<img src="${e.photo == '' ? 'photo/courseware.jpg' : e.photo}"/>
				<div class="name">${e.title }</div>
				<div class="face icon_face laugh"></div>
				<div class="operate  c_icon c_icon_exercise"></div>
			</div>
		</c:forEach>
	</div>
</div>