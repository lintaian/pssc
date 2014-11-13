<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="courseware">
	<div class="choose">
		<span>月份:</span>
		<c:forEach items="${obj.months }" var="m">
			<a data-date="${m.date }" class="month ${m.active ? 'active' : '' }">${m.name }</a>
		</c:forEach>
		<span>科目:</span>
			<a data-id="" class="subject ${(obj.subjectId == null || obj.subjectId == '') ? 'active' : '' }">全部</a>
		<c:forEach items="${obj.subjects }" var="s">
			<a data-id="${s._id }" class="subject ${obj.subjectId == s._id ? 'active' : '' }">${s.name }</a>
		</c:forEach>
	</div>
	<div class="coursewareList">
		<c:forEach items="${obj.cws }" var="c">
			<div class="item clickable" data-id="${c._id }" data-name="${c.title }">
				<img src="${c.photo == '' ? 'photo/courseware.jpg' : c.photo}" 
					class=""/>
				<div class="name">${c.title }</div>
				<div class="face icon_face laugh"></div>
			</div>
		</c:forEach>
	</div>
</div>