$(function() {
	$('.btn.save').click(function() {
		var cIds = $('select[name="class_id"]').val().split('-');
		var name = $('input[name="name"]').val();
		var login_name = $('input[name="login_name"]').val();
		if (name && login_name) {
			$.ajax({
				url: 'admin/student/update',
				type: 'post',
				data: JSON.stringify({
					id: $('input[name="id"]').val(),
					name: name,
					login_name: login_name,
					class_id: cIds[0],
					grade_id: cIds[1],
					birthday: $('input[name="birthday"]').val(),
					sex: $('select[name="sex"]').val(),
					status: $('select[name="status"]').val(),
					login_status: $('select[name="login_status"]').val(),
					auth_code: $('input[name="auth_code"]').val(),
					photo: $('.photo').attr('src')
				}),
				dataType: 'json',
				success: function(data2) {
					if (data2.status) {
						alert(data2.msg);
						window.location.href = 'admin/student';
					} else {
						alert(data2.msg);
					}
				}
			});
		} else {
			alert('姓名和登录名不能为空!');
		}
	});
	$('input[type="file"]').change(function() {
		$('.photo').attr('src', window.URL.createObjectURL(document.getElementById('photo').files[0]));
		var fd = new FormData(document.getElementById("fileUpload"));
		fd.append('maxSize', 400);
		$.ajax({
			url: "upload/img",
			type: "POST",
			data: fd,
			dataType: 'json',
			processData: false,  // tell jQuery not to process the data
			contentType: false,   // tell jQuery not to set contentType
			success: function(data) {
				if (data.status) {
					$('.photo').attr('src', data.url);
				}
			}
		});
	});
});
