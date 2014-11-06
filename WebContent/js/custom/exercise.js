define(['jquery'], function($) {
	$('body').on('click', '.e_answer', function() {
		var a = $(this).data('answer'),
			exerciseId = $('#exercise').data('id'),
			exerciseType = $('#exercise').data('type'),
			originAnswer = $('.e_my_answer_text').text(),
			single = $(this).parent('.e_answers').data('type') == 'single';
		if (!(single && a == originAnswer)) {
			var maxNum = $(this).parent('.e_answers').data('maxNum');
			maxNum = maxNum ? parseInt(maxNum) : 0;
			$.ajax({
				url: 'exercise/objective',
				type: 'get',
				data: 'exerciseId=' + exerciseId + '&answer=' + a + '&originAnswer=' + originAnswer + 
				'&single=' + single + '&maxNum=' + maxNum,
				dataType: 'json',
				success: function(data) {
					$('.e_my_answer_text').text(data.answer);
				}
			});
		}
	});
	$('body').on('change', '#exercise .upload input[type="file"]', function() {
		var val = $(this).val(),
			suffix = val.substr(val.lastIndexOf('.') + 1, val.length),
			eid = $('#exercise').data('id'),
			etype = $('#exercise').data('type'),
			url = 'upload/';
		suffix = suffix.toLowerCase();
		var fixRight = false;
		switch (etype) {
		case 20:
			if (suffix == 'png' || suffix == 'gif' || suffix == 'jpg' || suffix == 'jpeg') {
				fixRight = true;
				url += 'img';
			}
			break;
		case 21:
			if (suffix == 'acc' || suffix == 'mp3') {
				fixRight = true;
				url += 'audio';
			}
			break;
		case 22:
			if (suffix == 'mp4' || suffix == '3gp') {
				fixRight = true;
				url += 'video';
			}
			break;
		default:
			break;
		}
		if (fixRight) {
			var fd = new FormData(document.getElementById("answerUpload"));
			$.ajax({
				url: url,
				type: "POST",
				data: fd,
				dataType: 'json',
				processData: false,  // tell jQuery not to process the data
				contentType: false,   // tell jQuery not to set contentType
				success: function(data) {
					if (data) {
						$.ajax({
							url: 'exercise/subjective',
							type: 'get',
							data: {
								answer: data.url,
								eId: eid,
								eType: etype
							},
							dataType: 'json',
							success: function(d) {
								if (d.status) {
									Util.msg.show('提示信息', '文件上传成功!');
									$('.e_subjective_my_answer').attr('src', data.url).parent().show();
								} else {
									Util.msg.show('提示信息', '文件上传失败!');
								}
							}
						})
					} else {
						Util.msg.show('提示信息', data.msg, 'error');
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		} else {
			Util.msg.show('错误提示', '请选择正确格式的文件!', 'error');
		}
	});
	$('body').on('click', '.e_page .e_change', function() {
		var page = $(this).data('page');
		var exerciseBatchId = $('#exercise').data('exerciseBatchId');
		var parentEle = $('#exercise').data('parentEle');
		Util.load(parentEle, 'exercise', 
				'id=' + exerciseBatchId + '&page=' + page + '&parentEle=' + parentEle);
	});
	$('body').on('click', '#exercise .e_write .open_canvas', function() {
		if (!$('#fullIcon').parents('.content').hasClass('full')) {
			$('#fullIcon').click();
		}
		Util.load('.e_canvas', 'exercise/canvas', 'eid=' + $('#exercise').data('id'),
				function() {
			$('#canvas').height($('.outerPage').height());
			$('#fullIcon').addClass('unclickable');
			var canvas, context, img;
			canvas = document.getElementById('myCanvas');
			context = canvas.getContext('2d');
			var bgImg = $('.e_write').data('bgImg');
			if (bgImg) {
				img = new Image();
				img.src = bgImg;
				img.onload = function() {
					context.drawImage(img, 0, 0, 800, 600);
				}
			}
			context.fillStyle = "rgb(0,0,225)";
			context.strokeStyle = "rgb(0,0,0)";
		});
	});
});