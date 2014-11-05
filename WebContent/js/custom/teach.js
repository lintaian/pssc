define(['jquery'], function($) {
	var stateTime = '', timeout1, timeout2;
	/**
	 * 轮询查看是否上课
	 */
	function commonTimeout() {
		timeout1 = setTimeout(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				dataType: 'json',
				success: function(data) {
					if (data && data.c.status > 0) { //is teaching?
						var $target = $('[data-change-page="teach"]');
						var url = $target.data('changePage');
						Util.location.set([{
							url: url,
							name: $target.text()
						}]);
						$('#fullIcon').parents('.content').addClass('full');
						$('#contentModal').addClass('active');
						Util.load('.outerPage', url);
						teachingTimeout();
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
	function teachingTimeout() {
		timeout2 = setTimeout(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				dataType: 'json',
				success: function(data) {
					if (data && data.c.status > 0) {
						if (data.c.status == 2) {
							if (data.co.length > 0) {
								if (data.current && data.current.content_type == 3) {
									var obj = data.co[0];
									$.ajax({
										
									});
								} else {
									
								}
							} else {
								coverClass('waitTeach');
								teachingTimeout();
							}
						} else if (data.c.status == 1) {
							coverClass('beforeTeach');
							teachingTimeout();
						} else  if (data.c.status == 3) {
							coverClass('afterTeach');
							teachingTimeout();
						} else {
							window.location.reload();
						}
					} else {
						$('#fullIcon').parents('.content').removeClass('full');
						$('#contentModal').removeClass('active');
						coverClass('unTeach');
						commonTimeout();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		}, 1000);
	}
	
	function coverClass(cla) {
		$('#teach').removeClass().addClass(cla);
	}
//	commonTimeout();
});