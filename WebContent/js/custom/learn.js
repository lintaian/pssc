define(['jquery'], function($) {
	//student
	$('body').on('click', '.studentList .item.me', function() {
		Util.load('.outerPage', 'learn/courseware?init=true');
		Util.location.add({
			url: 'learn/courseware?init=true',
			name: '课件列表'
		});
	});
	//courseware
	$('body').on('change', '#courseware .choose select', function() {
		var subjectId = $('#subject').val() || '';
		var year = $('#year').val() || 0;
		var month = $('#month').val() || 0;
		var day = $('#day').val() || 0;
		Util.load('.outerPage', 'learn/courseware', {
				subjectId: subjectId,
				year: year,
				month: month,
				day: day
			});
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