define(['jquery'], function($) {
	//student
	$('body').on('click', '.studentList .item.me', function() {
		Util.load('.outerPage', 'learn/courseware');
		Util.location.add({
			url: 'learn/courseware',
			name: '课件列表'
		});
	});
	//courseware
	$('body').on('click', '#courseware .month', function() {
		var date = $(this).data('date');
		var subjectId = $('.subject.active').data('id');
		Util.load('.outerPage', 'learn/courseware', 'date=' + date + '&subjectId=' + subjectId);
	});
	$('body').on('click', '#courseware .subject', function() {
		var subjectId = $(this).data('id');
		var date = $('.month.active').data('date');
		Util.load('.outerPage', 'learn/courseware', 'date=' + date + '&subjectId=' + subjectId);
	});
	$('body').on('click', '.coursewareList .item', function() {
		var id = $(this).data("id");
		Util.load('.outerPage', 'learn/coursewareDetail', 'id=' + id);
		Util.location.add({
			url: 'learn/coursewareDetail?id=' + id,
			name: $(this).data('name')
		});
	});
	$('body').on('click', '#coursewareDetail .videoPackageList .operate', function() {
		var id = $(this).parent('.item').data('id');
		Util.load('.outerPage', 'video', 'id=' + id, function() {
			$('#video1').width($('.outerPage').width());
			$('#video1').height($('.outerPage').height());
		});
		Util.location.add({
			url: 'video?id=' + id,
			name: $(this).parent('.item').data('name'),
			fn: function locationFn() {
				$('#video1').width($('.outerPage').width());
				$('#video1').height($('.outerPage').height());
			}
		});
	});
	$('body').on('click', '#coursewareDetail .picturePackageList .operate', function() {
		var id = $(this).parent('.item').data('id');
		Util.load('.outerPage', 'picture', 'id=' + id, function() {
			$.ajax({
				url: 'picture/dict',
				type: 'get',
				data: {
					id: id
				},
				dataType: 'json',
				success: function(data) {
					Util.img.setData(data);
				}
			});
		});
		Util.location.add({
			url: 'picture?id=' + id,
			name: $(this).parent('.item').data('name'),
			id: id,
			fn: function locationFn(id) {
				$.ajax({
					url: 'picture/dict',
					type: 'get',
					data: {
						id: id
					},
					dataType: 'json',
					success: function(d) {
						Util.img.setData(d);
					}
				});
			}
		});
	});
	$('body').on('click', '#coursewareDetail .exercisePackageList .operate', function() {
		var id = $(this).parent('.item').data('id');
		Util.load('.outerPage', 'exercise', 'id=' + id + '&parentEle=.outerPage');
		Util.location.add({
			url: 'exercise?id=' + id,
			name: $(this).parent('.item').data('name')
		});
	});
});