<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="teach" class="${obj.currentClass }">
	<div class="un_teach_default">
		<div class="t_title">${obj.title }</div>
	</div>
	<div class="un_teach">
		<div class="t_title">${obj.title }</div>
		<img class="t_img" src="${obj.photo }">
	</div>
	<div class="teaching"></div>
</div>
