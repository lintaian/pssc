define(['jquery'], function($) {
	$('body').on('click', '.e_answer', function() {
		var a = $(this).data('answer'),
			exerciseId = $('#exercise').data('id'),
			exercisePackageId = $('#exercise').data('exercisePackageId'),
			exerciseType = $('#exercise').data('type'),
			originAnswer = $('.e_my_answer_text').text(),
			single = $(this).parent('.e_answers').data('type') == 'single';
		if (!(single && a == originAnswer)) {
			var maxNum = $(this).parent('.e_answers').data('maxNum');
			maxNum = maxNum ? parseInt(maxNum) : 0;
			$.ajax({
				url: 'exercise/objective',
				type: 'get',
				data: {
					exerciseId: exerciseId,
					answer: a,
					originAnswer: originAnswer,
					single: single,
					maxNum: maxNum,
					epId: exercisePackageId
				},
				dataType: 'json',
				success: function(data) {
					$('.e_my_answer_text').text(data.answer);
					var type = $('#exercise').data('type');
					if (Util.isLive && (type == 30 || type == 31)) {
						$.ajax({
							url: 'cache/vote',
							type: 'post',
							data: JSON.stringify({
								vid: $('#exercise').data('id'),
								data: data.answer
							})
						})
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		}
	});
	$('body').on('change', '#exercise .upload input[type="file"]', function() {
		var val = $(this).val(),
			suffix = val.substr(val.lastIndexOf('.') + 1, val.length),
			eid = $('#exercise').data('id'),
			epid = $('#exercise').data('exercisePackageId'),
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
			if (suffix == 'mp3' || suffix == 'acc' || suffix == 'amr') {
				fixRight = true;
				url += 'audio';
			}
			break;
		case 22:
			if (suffix == 'mp4' || suffix == '3gp' || suffix == 'mov') {
				fixRight = true;
				url += 'video';
			}
			break;
		default:
			break;
		}
		if (fixRight) {
			var fd = new FormData(document.getElementById("answerUpload"));
			fd.append('maxSize', 1024);
			Util.loader.show('文件上传中,请稍候...');
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
								eType: etype,
								epId: epid
							},
							dataType: 'json',
							success: function(d) {
								Util.loader.close();
								if (d.status) {
									Util.msg.show('提示信息', '文件上传成功!');
									$('.e_subjective_my_answer').attr('src', data.url).parent().show();
								} else {
									Util.msg.show('提示信息', '文件上传失败!');
								}
							},
							error: function(data) {
								Util.loader.close();
								Util.error(data);
							}
						})
					} else {
						Util.loader.close();
						Util.msg.show('提示信息', data.msg, 'error');
					}
				},
				error: function(data) {
					Util.loader.close();
					Util.error(data);
				}
			});
		} else {
			Util.msg.show('错误提示', '请选择正确格式的文件!', 'error');
		}
	});
	$('body').on('click', '.e_page .e_change', function() {
		var page = $(this).data('page');
		var exercisePackageId = $('#exercise').data('exercisePackageId');
		var parentEle = $('#exercise').data('parentEle');
		Util.load(parentEle, 'exercise', 
				'id=' + exercisePackageId + '&page=' + page + '&parentEle=' + parentEle);
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
			context.clearRect(0,0,800,600);
			var bgImg = $('.e_write').data('bgImg');
			if (bgImg) {
				img = new Image();
				img.src = bgImg;
				img.onload = function() {
					context.drawImage(img, 0, 0, 800, 600);
					context.save();
				}
			}
			context.fillStyle = "rgb(0,0,225)";
			context.strokeStyle = "rgb(0,0,0)";
		});
	});
	$('body').on('click', '.e_finish', function(e) {
		if ($('#exercise').parent().hasClass('picture_exercise')) {
			$('.picture_exercise').empty();
			$('.picture').css('top', 0);
			Util.location.remove(-1);
		} else if ($('#exercise').parent().hasClass('video_exercise')) {
			$('.video_exercise').empty();
			$('#video1').css({'top': 0});
			$('#video1').width($('#video').width());
			Util.location.remove(-1);
			document.getElementById("video1").play();
		} else {
			if (Util.isLive) {
				$.ajax({
					url: 'teach/contentStatus',
					type: 'post',
					data: JSON.stringify({
						id: $('.teaching').data('opRealId')
					}),
					dataType: 'json',
					success: function(data) {
					},
					error: function(data) {
						Util.error(data);
					}
				});
			} else {
				Util.location.jump(-2);
			}
		}
	});
});