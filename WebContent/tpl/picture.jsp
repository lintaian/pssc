<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="picture" data-id="${obj.picturePackage._id }">
	<div class="picture">
		<div class="p_img">
			<div class="p_title">${obj.picturePackage.title }</div>
			<img src="">
		</div>
		<div class="p_page"></div>
	</div>
	<div class="picture_exercise hide"></div>
</div>