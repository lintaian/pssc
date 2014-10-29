<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="exercise">
	<div class="e_question">
		<img src="${obj.exercise.url }">
	</div>
	<c:choose>
		<c:when test="${obj.exercise.exercise_type == 1 }">
			<div class="e_my_answer">
				答案: ( A )
			</div>
			<div class="e_answers">
				<c:forEach begin="0" end="${obj.exercise.exercise_info.answerNum - 1 }" varStatus="a">
					<div class="e_answer">
						<img class="e_answer_img" src="img/a${a.index }.jpg">
						<div class="e_answer_text">
							${obj.answer[a.index] }
						</div>
					</div>
				</c:forEach>
			</div>
		</c:when>
	</c:choose>
	<div class="e_page">
		<c:if test="${obj.page.curPage > 1 }">
			<span class="e_prev">上一题</span>
		</c:if>
		<c:if test="${obj.page.curPage < obj.page.countPage }">
			<span class="e_next">下一题</span>
		</c:if>
		<c:if test="${obj.page.curPage == obj.page.countPage }">
			<span class="e_finish">完成</span>
		</c:if>
		${obj.page.curPage } / ${obj.page.countPage }
	</div>
</div>
<script>
	
</script>