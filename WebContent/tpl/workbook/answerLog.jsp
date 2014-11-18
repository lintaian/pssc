<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="answerLog">
	<c:forEach items="${obj.als }" var="a" varStatus="i">
		<div class="answerlog ${i.index%2 == 0 ? '' : 'gray' }">
			<span class="answer-date">
				<fmt:formatDate value="${a.answer_date }" pattern="yyyy年MM月dd日  HH:mm:ss"/>
			</span>
			<c:choose>
				<c:when test="${obj.ex.exercise_type ==  10 || obj.ex.exercise_type == 11 }">
					<span>选择了 ( ${a.answer } )</span>
				</c:when>
				<c:when test="${obj.ex.exercise_type == 20 || obj.ex.exercise_type == 23 }">
					上传了图片 <img class="viewable" src="${a.answer }">
				</c:when>
				<c:when test="${obj.ex.exercise_type == 21 }">
					上传了录音 <audio controls="controls">
					  	<source src="${a.answer }" type="audio/ogg">
					  	<source src="${a.answer }" type="audio/mpeg">
					  	<source src="${a.answer }" type="audio/wav">
						Your browser does not support the audio element.
					</audio>
				</c:when>
				<c:when test="${obj.ex.exercise_type == 22 }">
					上传了视频 <video controls="controls">
						<source src="${a.answer }" type="video/ogg" />
						<source src="${a.answer }" type="video/mp4" />
						<source src="${a.answer }" type="video/webm" />
						Your browser does not support the video tag.
					</video>
				</c:when>
			</c:choose>
		</div>
	</c:forEach>
	<div class="btns">
		<span class="btn back">返回</span>
	</div>
</div>