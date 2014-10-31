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
		<c:when test="${obj.exercise.exercise_type == 20 || obj.exercise.exercise_type == 21 || obj.exercise.exercise_type == 22 }">
			<div class="e_my_answer">
				我的答案 :
			</div>
			<div class="e_my_answer">
				<c:choose>
					<c:when test="${obj.exercise.exercise_type == 20 }">
						<img class="e_subjective_my_answer e_my_img ${obj.myAnswer.answer == '' ? 'hide' : '' }" 
							src="${obj.myAnswer.answer }">
					</c:when>
					<c:when test="${obj.exercise.exercise_type == 21 }">
						<audio class="e_subjective_my_answer e_my_audio ${obj.myAnswer.answer == '' ? 'hide' : '' }" 
							controls="controls">
						  	<source src="${obj.myAnswer.answer }" type="audio/ogg">
						  	<source src="${obj.myAnswer.answer }" type="audio/mpeg">
						  	<source src="${obj.myAnswer.answer }" type="audio/wav">
							Your browser does not support the audio element.
						</audio>
					</c:when>
					<c:when test="${obj.exercise.exercise_type == 22 }">
						<video class="e_subjective_my_answer e_my_video ${obj.myAnswer.answer == '' ? 'hide' : '' }" 
							controls="controls" preload="metadata">
							<source src="${obj.myAnswer.answer }" type="video/ogg" />
							<source src="${obj.myAnswer.answer }" type="video/mp4" />
							<source src="${obj.myAnswer.answer }" type="video/webm" />
							Your browser does not support the video tag.
						</video>
					</c:when>
				</c:choose>
			</div>
			<div class="e_subjective">
				<div class="upload e_upload">
					<span class="btn">上传${obj.exercise.exercise_type == 20 ? '图片' : (obj.exercise.exercise_type == 21 ? '录音' : (obj.exercise.exercise_type == 22 ? '视频' : '')) }</span>
					<form method="post" id="answerUpload"
						enctype="multipart/form-data">
						<input type="file" name="file">				
					</form>
				</div>
				<div class="qrcode">
					<img alt="" src="qrcode/${obj.exercise._id }/${obj.exercise.exercise_type }">
				</div>
			</div>
		</c:when>
	</c:choose>
	<div class="e_page">
		<c:if test="${obj.page.curPage > 1 }">
			<span class="e_change btn" data-page="${obj.page.curPage - 1 }">上一题</span>
		</c:if>
		<c:if test="${obj.page.curPage < obj.page.countPage }">
			<span class="e_change btn" data-page="${obj.page.curPage + 1 }">下一题</span>
		</c:if>
		<c:if test="${obj.page.curPage == obj.page.countPage }">
			<span class="e_finish btn">完成</span>
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