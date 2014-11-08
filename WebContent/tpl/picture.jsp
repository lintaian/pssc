<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="picture" data-id="${obj.picture_batch._id }" data-cw-id="${obj.cw_id }" data-cw-type="${obj.cw_type }">
	<div class="p_img">
		<div class="${obj.picture.url == '' ? 'no_img' : '' }">${obj.picture.title }</div>
		<img src="${obj.picture.url }">
	</div>
	<div class="picture_exercise hide"></div>
	<div class="p_page">
		<c:if test="${obj.page.curPage > 1 }">
			<span class="p_change btn" data-page="${obj.page.curPage - 1 }">上一题</span>
		</c:if>
		<c:if test="${obj.page.curPage < obj.page.countPage }">
			<span class="p_change btn" data-page="${obj.page.curPage + 1 }">下一题</span>
		</c:if>
		<c:if test="${obj.page.curPage == obj.page.countPage }">
			<span class="p_finish btn">完成</span>
		</c:if>
		<c:if test="${obj.page.countPage != 1 }">
			${obj.page.curPage } / ${obj.page.countPage }
		</c:if>
	</div>
</div>