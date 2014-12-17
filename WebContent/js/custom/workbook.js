define(['jquery'], function($) {
	$('body').on('change', '#workbookExerciseList .choose select', function() {
		var subjectId = $('#subject').val() || '';
		var year = $('#year').val() || 0;
		var month = $('#month').val() || 0;
		var day = $('#day').val() || 0;
		Util.load('.outerPage', 'workbook', {
				subjectId: subjectId,
				year: year,
				month: month,
				day: day
			});
	});
	$('body').on('click', '#workbookExerciseList .exerciseList .item', function() {
		var name = $(this).data('name');
		var id = $(this).data('id');
		Util.load('.outerPage', 'workbook/exercise/' + id, null, function() {
			Util.location.add({
				name: name,
				url: 'workbook/exercise/' + id
			});
		});
	});
	$('body').on('click', '.to-answer-log', function() {
		var id = $('#exerciseDetail').data('id');
		Util.load(".outerPage", 'workbook/exercise/answerlog/' + id, null, function() {
			Util.location.add({
				name: '回答轨迹',
				url: 'workbook/exercise/answerlog/' + id
			});
		});
	});
	$('body').on('click', '#answerLog .back', function() {
		Util.location.jump(-2);
	});
});