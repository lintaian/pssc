<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="courseware">
	<%-- <div class="choose">
		<span>月份:</span>
		<c:forEach items="${obj.months }" var="m">
			<a data-date="${m.date }" class="month ${m.active ? 'active' : '' }">${m.name }</a>
		</c:forEach>
		<span>科目:</span>
			<a data-id="" class="subject ${(obj.subjectId == null || obj.subjectId == '') ? 'active' : '' }">全部</a>
		<c:forEach items="${obj.subjects }" var="s">
			<a data-id="${s._id }" class="subject ${obj.subjectId == s._id ? 'active' : '' }">${s.name }</a>
		</c:forEach>
	</div> --%>
	<div class="choose">
		<div>
			<select id="subject">
				<option ${(obj.subjectId == null || obj.subjectId == '') ? 'selected' : '' } value="">全部科目</option>
				<c:forEach items="${obj.subjects }" var="s">
					<option ${obj.subjectId == s._id ? 'selected' : '' } value="${s._id }">${s.name }</option>
				</c:forEach>
			</select>
		</div>
		<div>
			<select id="year">
				<option ${obj.date.current_year == 0 ? 'selected' : '' } value="0">全部时间</option>
				<c:forEach begin="2014" end="2050" var="y">
					<option ${obj.date.current_year == y ? 'selected' : '' } value="${y }">${y }年</option>
				</c:forEach>
			</select>
		</div>
		<c:if test="${obj.date.current_year != 0 }">
			<div>
				<select id="month">
					<option ${obj.date.current_month == 0 ? 'selected' : '' } value="0">全年</option>
					<c:forEach begin="1" end="12" var="m">
						<option ${obj.date.current_month == m ? 'selected' : '' } value="${m }">${m }月</option>
					</c:forEach>
				</select>
			</div>
			<c:if test="${obj.date.current_month != 0 }">
				<div>
					<select id="day">
						<option ${obj.date.current_day == 0 ? 'selected' : '' } value="0">全月</option>
						<c:forEach begin="1" end="${obj.date.max_day }" var="d">
							<option ${obj.date.current_day == d ? 'selected' : '' } value="${d }">${d }日</option>
						</c:forEach>
					</select>
				</div>
			</c:if>
		</c:if>
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