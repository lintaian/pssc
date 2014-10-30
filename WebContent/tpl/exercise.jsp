<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="exercise" data-type="${obj.exercise.exercise_type }"
	data-id="${obj.exercise._id }" data-exercise-batch-id="${obj.exerciseBatchId }">
	<div class="e_question">
		<img src="${obj.exercise.url }">
	</div>
	<c:choose>
		<c:when test="${obj.exercise.exercise_type == 10 || obj.exercise.exercise_type == 11 }">
			<div class="e_my_answer">
				我的答案: ( <span class="e_my_answer_text">${obj.myAnswer.answer }</span> )
			</div>
			<div class="e_answers">
				<c:forEach begin="0" end="${obj.exercise.exercise_info.answerNum - 1 }" varStatus="a">
					<div class="e_answer ${obj.exercise.exercise_type == 10 ? 'single' : 'multi' }" 
						data-answer="${obj.answer[a.index] }">
						<img class="e_answer_img" src="img/a${a.index }.jpg">
						<div class="e_answer_text">
							${obj.answer[a.index] }
						</div>
					</div>
				</c:forEach>
			</div>
		</c:when>
		<c:when test="${obj.exercise.exercise_type == 20 }">
			<div class="e_my_answer">
				我的答案 :
			</div>
			<div class="e_my_answer">
				<img class="e_subjective_my_answer ${obj.myAnswer.answer == '' ? 'hide' : '' }" 
					src="${obj.myAnswer.answer }">
			</div>
			<div class="e_subjective">
				<div class="uploadImg e_upload">
					<span>上传图片</span>
					<form action="user/uploader" method="post" id="answerUpload"
						enctype="multipart/form-data">
						<input type="file" name="file">				
					</form>
				</div>
				<div class="qrcode">
					<img alt="" src="photo/q1.png">
				</div>
			</div>
		</c:when>
	</c:choose>
	<div class="e_page">
		<c:if test="${obj.page.curPage > 1 }">
			<span class="e_change" data-page="${obj.page.curPage - 1 }">上一题</span>
		</c:if>
		<c:if test="${obj.page.curPage < obj.page.countPage }">
			<span class="e_change" data-page="${obj.page.curPage + 1 }">下一题</span>
		</c:if>
		<c:if test="${obj.page.curPage == obj.page.countPage }">
			<span class="e_finish">完成</span>
		</c:if>
		<c:if test="${obj.page.countPage != 1 }">
			${obj.page.curPage } / ${obj.page.countPage }
		</c:if>
	</div>
</div>
<script>
	$(function() {
		$('.e_page').delegate('.e_change', 'click', function() {
			var page = $(this).data('page');
			var exerciseBatchId = $('#exercise').data('exerciseBatchId');
			Util.load('.video_exercise', 'exercise', 
					'exerciseBatchId=' + exerciseBatchId + '&page=' + page);
		});
	});
</script>