define(['jquery'], function($) {
	$(function() {
		$('body').on('click', '#updatePwd', function() {
			var pwd = lock.getPattern() || '';
			if (pwd == '') {
				Util.msg.show('提示信息', '更新失败,密码不能为空!', 'warning');
			} else {
				$.ajax({
					url: 'user/updatePwd',
					type: 'post',
					data: JSON.stringify({
						pwd: pwd
					}),
					dataType: 'json',
					success: function(data) {
						$('#info').find('.passwordText').val('');
						$('#info').find('.password').text('');
						if (data.status) {
							Util.msg.show('提示信息', '密码更新成功!');
						} else {
							Util.msg.show('<span style="color: red">提示信息</span>', '密码更新失败!');
						}
					}
				});
			}
		});
		$('body').on('click', '#info .password', function() {
			$('.pwdList').toggle();
		});
		
		
		$('body').on('change', '#uploadPhoto input[type="file"]', function() {
			var fd = new FormData(document.getElementById("uploadForm"));
			fd.append("name", "This is some extra data");
			$.ajax({
				url: "user/uploader",
				type: "POST",
				data: fd,
				dataType: 'json',
				processData: false,  // tell jQuery not to process the data
				contentType: false,   // tell jQuery not to set contentType
				success: function(data) {
					if (data) {
						$('#myPhoto').attr('src', data.photo);
					}
				}
			});
		});
	});
});