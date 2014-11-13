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
			error: function() {
				$(this).removeClass('on');
			}
		});
	});
	$('body').on('click', '.preemptive_finish', function() {
		var id = $('#preemptive').data('id');
		$.ajax({
			url: 'teach/contentStatus',
			type: 'post',
			data: JSON.stringify({
				content_id: id
			}),
			dataType: 'json',
			success: function(data) {
			}
		});
	})
});