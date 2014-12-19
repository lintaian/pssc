<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="exercise" data-type="${obj.exercise.exercise_type }" data-title="${obj.exercise.title }"
	data-id="${obj.exercise._id }" data-exercise-package-id="${obj.exercisePackageId }"
	data-parent-ele="${obj.parentEle }">
	<div class="e_question">
		<div class="${obj.exercise.url == '' ? 'no_img' : '' }">${obj.exercise.title }</div>
		<img class="viewable" src="${obj.exercise.url }">
	</div>
	<c:choose>
		<c:when test="${obj.exercise.exercise_type == 10 || obj.exercise.exercise_type == 11 
						|| obj.exercise.exercise_type == 30 || obj.exercise.exercise_type == 31}">
			<div class="e_my_answer">
				我的答案: ( <span class="e_my_answer_text">${obj.myAnswer.answer }</span> )
			</div>
			<div class="e_answers" data-type="${(obj.exercise.exercise_type == 10 || obj.exercise.exercise_type == 30) ? 'single' : 'multi' }">
				<c:forEach begin="0" end="${obj.exercise.exercise_info.answer_num - 1 }" varStatus="a">
					<div class="e_answer" 
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
			<div class="e_my_answer e_subjective_answer ${(obj.myAnswer != null && obj.myAnswer.answer != '') ? '' : 'hide'}">
				<c:choose>
					<c:when test="${obj.exercise.exercise_type == 20 }">
						<img class="e_subjective_my_answer e_my_img viewable" 
							src="${obj.myAnswer.answer }">
					</c:when>
					<c:when test="${obj.exercise.exercise_type == 21 }">
						<audio class="e_subjective_my_answer e_my_audio" 
							controls="controls">
						  	<source src="${obj.myAnswer.answer }" type="audio/ogg">
						  	<source src="${obj.myAnswer.answer }" type="audio/mpeg">
						  	<source src="${obj.myAnswer.answer }" type="audio/wav">
							Your browser does not support the audio element.
						</audio>
					</c:when>
					<c:when test="${obj.exercise.exercise_type == 22 }">
						<video class="e_subjective_my_answer e_my_video" 
							controls="controls">
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
					<img alt="" src="qrcode/${obj.exercise._id }/${obj.exercise.exercise_type }/${obj.exercisePackageId }">
				</div>
			</div>
		</c:when>
		<c:when test="${obj.exercise.exercise_type == 23 }">
			<div class="e_my_answer">
				我的答案 : 
			</div>
			<div class="e_subjective_answer">
				<img class="e_subjective_my_answer e_my_write ${(obj.myAnswer != null && obj.myAnswer.answer != '') ? '' : 'hide'}" 
							src="${obj.myAnswer.answer }">
			</div>
			<div class="e_write" data-bg-img="${obj.exercise.exercise_info.bg_img }">
				<span class="btn open_canvas">手写回答</span>
				<div class="e_canvas"></div>
			</div>
		</c:when>
	</c:choose>
	<div class="e_page">
		<c:if test="${obj.page.curPage > 1 }">
			<span class="e_change btn" data-page="${obj.page.curPage - 1 }">上一题</span>
		</c:if>
		<c:if test="${obj.page.countPage != 1 }">
			${obj.page.curPage } / ${obj.page.countPage }
		</c:if>
		<c:if test="${obj.page.curPage < obj.page.countPage }">
			<span class="e_change btn" data-page="${obj.page.curPage + 1 }">下一题</span>
		</c:if>
		<c:if test="${obj.page.curPage == obj.page.countPage }">
			<span class="e_finish btn">完成</span>
		</c:if>
	</div>
</div>