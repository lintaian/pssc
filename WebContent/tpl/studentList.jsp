<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="learn">
	<c:forEach items="${obj }" var="o">
		<div class="student ${o._id==loginUser._id ? 'me' : '' }">
			<img src="${o.Photo }" class="${o.LoginStatus==1 ? '' : 'cover' }"/>
			<div class="s_name ${o._id==loginUser._id ? 'me' : '' } ${o.Sex==1 ? 'girl' : 'boy' }">${o.Name }</div>
			<img class="face" src="img/face01.png"/>
		</div>
	</c:forEach>
</div>