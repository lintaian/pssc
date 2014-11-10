<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="picture" data-id="${obj.picture_package._id }" data-cw-id="${obj.cw_id }" data-cw-type="${obj.cw_type }">
	<div class="picture">
		<div class="p_img">
			<div class="p_title">${obj.picture_package.title }</div>
			<img src="">
		</div>
		<div class="p_page"></div>
	</div>
	<div class="picture_exercise hide"></div>
</div>