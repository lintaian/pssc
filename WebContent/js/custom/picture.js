define(['jquery'], function($) {
	
	$('body').on('click', '.p_page .p_prev', function() {
		Util.img.prev();
	});
	$('body').on('click', '.p_page .p_next', function() {
		Util.img.next();
	});
	$('body').on('click', '.p_page .p_finish', function() {
		if (sessionStorage.isLive == 'true') {
			$.ajax({
				url: 'teach/contentStatus',
				type: 'post',
				data: JSON.stringify({
					content_id: $('#picture').data('id')
				}),
				dataType: 'json',
				success: function(data) {
				}
			});
		} else {
			Util.location.jump(-2);
		}
	});
});