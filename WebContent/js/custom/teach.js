define(['jquery'], function($) {
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
						if (!$('#fullIcon').parents('.content').hasClass('full')) {
							$('#fullIcon').click();
						}
						$('#contentModal').addClass('active');
						var currentClass;
						switch (data.c_status) {
						case 1:
							currentClass = 'p_beforeTeach';
							break;
						case 2:
							if (data.wait) {
								currentClass = 'p_waitTeach';
							} else {
								currentClass = 'p_beforeTeach';
							}
							break;
						case 3:
							currentClass = 'p_afterTeach';
							break;

						default:
							break;
						}
						Util.load('.outerPage', url, 'currentClass=' + currentClass + '&coursewareId=' + data.courseware_id,
								function() {
							parseData(data);
						});
					} else {
						commonTimeout();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			})
		}, 5000);
	}
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
						$('#fullIcon').click();
						$('#contentModal').removeClass('active');
						coverClass('p_unTeach');
						commonTimeout();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		}, 500);
	}
	
	function coverClass(cla) {
		$('#teach').removeClass().addClass(cla);
	}
	function parseData(data) {
		if (data.c_status == 2) {
			if (data.wait) {
				coverClass('p_waitTeach');
			}
			if (data.op_type && data.op_id) {
				var url = '', data2 = 'id=' + data.op_id;
				switch (data.op_type) {
				case 0:
					url = 'video';
					break;
				case 1:
					url = 'exercise';
					data2 += '&parentEle=.teaching';
					break;
				case 2:
					url = 'picture/single';
					break;
				default:
					break;
				}
				Util.load('.teaching', url, data2);
				coverClass('p_teaching');
			}
			teachingTimeout();
		} else if (data.c_status == 1) {
			coverClass('p_beforeTeach');
			teachingTimeout(true);
		} else  if (data.c_status == 3) {
			coverClass('p_afterTeach');
			teachingTimeout(true);
		} else {
			window.location.reload();
		}
	}
//	commonTimeout();
});