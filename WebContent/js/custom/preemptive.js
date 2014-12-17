define(['jquery'], function($) {
	$('body').on('click', '.preemptive:not(.on)', function() {
		var id = $('#preemptive').data('id');
		var $this = $(this);
		$.ajax({
			url: 'preemptive',
			type: 'post',
			data: JSON.stringify({
				id: id
			}),
			dataType: 'json',
			success: function(data) {
				if (data.status) {
					$this.addClass('on');
					$('.preemptive_finish').show();
					$.ajax({
						url: 'cache/preemptive',
						type: 'post',
						data: JSON.stringify({
							pid: id,
							timestamp: data.timestamp
						})
					});
				}
			},
			error: function(data) {
				$(this).removeClass('on');
				Util.error(data);
			}
		});
	});
	$('body').on('click', '.preemptive_finish', function() {
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
	})
});