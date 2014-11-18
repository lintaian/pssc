<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="record">
	<c:forEach items="${obj.data}" var="r">
		<span class="time">
			<fmt:formatDate value="${r.create_date }" pattern="yyyy年MM月dd日  HH:mm:ss" />
		</span>
		<span class="operate">${r.operate}</span>
		<hr/>
	</c:forEach>
	<div id="recordPage" class="page">
		<a class="${obj.page.curPage==1?'':'clickable'}" data-page="1">首页</a>
		<a class="${obj.page.curPage>1?'clickable':''}" data-page="${obj.page.curPage-1}">上一页</a>
		<a class="${obj.page.curPage<obj.page.countPage?'clickable':''}" data-page="${obj.page.curPage+1}">下一页</a>
		<a class="${obj.page.curPage==obj.page.countPage?'':'clickable'}" data-page="${obj.page.countPage}">末页</a>
		<span>(共 ${obj.page.count} 条记录,每页 ${obj.page.perPage} 条,当前第 ${obj.page.curPage}/${obj.page.countPage} 页)</span>
	</div>
</div>
