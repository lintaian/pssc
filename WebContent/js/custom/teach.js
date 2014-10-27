define(['jquery'], function($) {
	var stateTime = '', interval1, interval2;
	/**
	 * 轮询查看是否上课
	 */
	function commonInterval() {
		interval1 = setInterval(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				dataType: 'json',
				success: function(data) {
					if (data.teaching == 1) { //is teaching?
						var $target = $('[data-change-page="tpl/classRoom.html"]');
						var url = $target.data('changePage');
						$('#location').text($target.text());
						$('.outerPage').load(url, function() {
							$('#teaching').show();
							$('#unTeaching').hide();
						});
						$('#fullIcon').click();
						$('#contentModal').addClass('active');
						clearInterval(interval1);
						teachingInerval();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			})
		}, 5000);
	}
	function teachingInerval() {
		interval2 = setInterval(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				dataType: 'json',
				success: function(data) {
					if (data.teaching == 1) {
						if (data.stateTime != stateTime) {
							switch (data.state) {
							case 'img':
								$('#teaching').html('<img class="img" src="' + data.remark + '">');
								break;
							case 'write': 
								break;
							case 'question': 
								break;
							case 'vote': 
								break;
							case 'firstAnswer':
								break;
							default:
								break;
							}
							stateTime = data.stateTime;
						}
					} else {
						$('#teaching').hide();
						$('#unTeaching').show();
						$('#fullIcon').click();
						$('#contentModal').removeClass('active');
						clearInterval(interval2);
						commonInterval();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		}, 1000);
	}
//	commonInterval();
});