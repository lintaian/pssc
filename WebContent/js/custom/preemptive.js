define(['jquery'], function($) {
	$('body').on('click', '.preemptive:not(.on)', function() {
		var id = $('#preemptive').data('id');
		$.ajax({
			url: 'preemptive',
			type: 'post',
			data: JSON.stringify({
				id: id
			}),
			dataType: 'json',
			success: function(data) {
				if (data.status) {
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
				}
			}
		});
	})
});