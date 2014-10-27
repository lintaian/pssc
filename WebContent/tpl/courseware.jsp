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
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
		<div class="courseware">
			<img src="photo/001.jpg" 
				class=""/>
			<div class="c_name">aa</div>
			<div class="face icon_face smile"></div>
		</div>
	</div>
</div>