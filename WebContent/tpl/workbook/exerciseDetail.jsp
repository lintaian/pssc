<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="exerciseDetail" data-id="${obj.a._id }">
	<div class="block-title">基本信息</div>
	<div class="block-content">
		<table>
			<tr>
				<td class="td-name">所属科目 :</td>
				<td class="td-content">${obj.sj.name }</td>
				<td class="td-name">习题类型 :</td>
				<c:set var="t" value="${obj.ex.exercise_type }"></c:set>
				<td class="td-content">${t == 10 ? '单项选择题' : t == 11 ? '多项选择题' : t == 20 ? '主观题(图片回答)' :
					t == 21 ? '主观题(录音回答)' : t == 22 ? '主观题(视频回答)' : t == 23 ? '主观题(手写回答)' : '' }</td>
			</tr>
			<tr>
				<td class="td-name">所属课件 :</td>
				<td class="td-content">${obj.cw.title }</td>
				<td class="td-name">习题总分 :</td>
				<td class="td-content">${obj.ex.scores }</td>
			</tr>
			<tr>
				<td class="td-name">所属习题包 :</td>
				<td class="td-content">${obj.ep.title }</td>
				<td class="td-name">我的得分 :</td>
				<td class="td-content">${obj.a.scores == -1 ? '未批阅' : obj.a.scores }</td>
			</tr>
		</table>
	</div>
	<div class="block-title">习题内容</div>
	<div class="block-content">
		<div class="e-title">${obj.ex.title }</div>
		<img class="e-img viewable" src="${obj.ex.url }">
	</div>
	<div class="block-title">我的答案</div>
	<div class="block-content answer">
		<c:choose>
			<c:when test="${obj.ex.exercise_type ==  10 || obj.ex.exercise_type == 11 }">
				<span>( ${obj.a.answer } )</span>
			</c:when>
			<c:when test="${obj.ex.exercise_type == 20 || obj.ex.exercise_type == 23 }">
				<img class="viewable" src="${obj.a.answer }">
			</c:when>
			<c:when test="${obj.ex.exercise_type == 21 }">
				<audio controls="controls">
				  	<source src="${obj.a.answer }" type="audio/ogg">
				  	<source src="${obj.a.answer }" type="audio/mpeg">
				  	<source src="${obj.a.answer }" type="audio/wav">
					Your browser does not support the audio element.
				</audio>
			</c:when>
			<c:when test="${obj.ex.exercise_type == 22 }">
				<video controls="controls">
					<source src="${obj.a.answer }" type="video/ogg" />
					<source src="${obj.a.answer }" type="video/mp4" />
					<source src="${obj.a.answer }" type="video/webm" />
					Your browser does not support the video tag.
				</video>
			</c:when>
		</c:choose>
	</div>
	<div class="btns">
		<span class="btn to-answer-log">查看回答轨迹</span>
	</div>
</div>