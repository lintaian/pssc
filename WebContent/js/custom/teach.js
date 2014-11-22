define(['jquery'], function($) {
	var stateCode = {
		unTeach: '现在还未到上课时间,请小泡泡们先去预习功课吧!',
		beforeTeach: '请小泡泡们安静的等待老师上课!',
		waitTeach: '请小泡泡们等待老师操作!',
		afterTeach: '上课结束,请小泡泡们下课后按时完成老师布置的作业哦!' 
	};
	/**
	 * 轮询查看是否上课
	 */
	function commonTimeout() {
		setTimeout(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				dataType: 'json',
				data: {
					init: true
				},
				success: function(data) {
					if (data.status && data.c_status > 0) { //is teaching?
						var $target = $('[data-change-page="teach"]');
						var url = $target.data('changePage');
						Util.location.set([{
							url: url,
							name: $target.text()
						}]);
						var currentClass, 
						title = data.c_title;
						if (data.c_photo && data.c_photo != '') {
							currentClass = 'p_un_teach';
						} else {
							currentClass = 'p_un_teach_default';
						}
						switch (data.c_status) {
						case 1:
							title = title || stateCode.beforeTeach;
							break;
						case 2:
							if (data.wait) {
								title = title || stateCode.waitTeach;
							} else {
								currentClass = 'p_teaching';
							}
							break;
						case 3:
							title = title || stateCode.afterTeach;
							break;
						default:
							break;
						}
						Util.load('.outerPage', url, 'currentClass=' + currentClass + 
								'&coursewareId=' + data.courseware_id +
								'&photo=' + data.c_photo +
								'&title=' + title,
								function() {
							if (!$('#fullIcon').parents('.content').hasClass('full')) {
								$('#fullIcon').click();
							}
							$('#contentModal').addClass('active');
							$('.left li').removeClass('active');
							$('.left li:first').addClass('active');
							parseData(data);
						});
						Util.isLive = true;
					} else {
						commonTimeout();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			})
		}, 5000);
	};
	function teachingTimeout(init) {
		setTimeout(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				data: {
					init: init
				},
				dataType: 'json',
				success: function(data) {
					if (data.status && data.c_status > 0) {
						parseData(data);
					} else {
						coverClass(false, data.c_photo, data.c_title || stateCode.unTeach);
						Util.isLive = false;
						$('#contentModal').removeClass('active');
						$('#fullIcon').removeClass('unclickable');
						if ($('#fullIcon').parents('.content').hasClass('full')) {
							$('#fullIcon').click();
						}
						commonTimeout();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		}, 1000);
	};
	
	function coverClass(teaching, photo, title) {
		if (teaching) {
			$('#teach').removeClass().addClass('p_teaching');
		} else {
			if (photo && photo != '') {
				$('#teach').removeClass().addClass('p_un_teach');
				$('#teach').find('.t_img').attr('src', photo);
			} else {
				$('#teach').removeClass().addClass('p_un_teach_default');
			}
			$('#teach').find('.t_title').text(title);
		}
	};
	function parseData(data) {
		if (data.c_status == 2) {
			if (data.wait) {
				coverClass(false, data.c_photo, data.c_title || stateCode.waitTeach);
			}
			if ((data.op_type || data.op_type == 0) && data.op_id) {
				var url = '', data2 = 'id=' + data.op_id;
				if (data.op_type == 0) {
					Util.load('.teaching', 'video', data2, function() {
						$('#video1').width($('.outerPage').width());
						$('#video1').height($('.outerPage').height());
					});
				} else if (data.op_type == 1) {
					data2 += '&parentEle=.teaching';
					Util.load('.teaching', 'exercise', data2);
				} else if (data.op_type == 2) {
					Util.load('.teaching', 'picture', data2, function() {
						$.ajax({
							url: 'picture/dict',
							type: 'get',
							data: {
								id: data.op_id
							},
							dataType: 'json',
							success: function(data) {
								Util.img.setData(data);
							}
						});
					});
				} else if (data.op_type == 3) {
					Util.load('.teaching', 'preemptive', data2);
				} else if (data.op_type == 4) {
					Util.load('.teaching', 'picture/single', data2);
				}
				coverClass(true);
			}
			teachingTimeout();
			$('.teaching').data('opRealId', data.op_real_id);
		} else if (data.c_status == 1) {
			coverClass(false, data.c_photo, data.c_title || stateCode.beforeTeach);
			teachingTimeout(true);
		} else  if (data.c_status == 3) {
			coverClass(false, data.c_photo, data.c_title || stateCode.afterTeach);
			teachingTimeout(true);
		} else {
			window.location.reload();
		}
	};
	commonTimeout();
});