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
						Util.load('.outerPage', url);
						teachingTimeout(true);
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
						if (data.c_status == 2) {
							if (data.op_type && data.op_id) {
								var url = '', data = 'id=' + data.op_id;
								switch (data.op_type) {
								case 1:
									url = 'exercise';
									data += '&parentEle=.teaching';
									break;
								case 2:
									url = 'video';
									break;
								case 3:
									url = 'picture/single';
									break;
								default:
									break;
								}
								Util.load('.teaching', url, data);
								coverClass('teaching');
							} else {
								coverClass('waitTeach');
							}
						} else if (data.c_status == 1) {
							coverClass('beforeTeach');
						} else  if (data.c_status == 3) {
							coverClass('afterTeach');
						} else {
							window.location.reload();
						}
						teachingTimeout();
					} else {
						$('#fullIcon').click();
						$('#contentModal').removeClass('active');
						coverClass('unTeach');
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
	commonTimeout();
});