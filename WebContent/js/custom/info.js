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
					},
					error: function(data) {
						Util.error(data);
					}
				});
			}
		});
		$('body').on('click', '#info .password', function() {
			$('.pwdList').toggle();
		});
		
		
		$('body').on('change', '.uploadImg input[type="file"]', function() {
			var val = $(this).val();
			var suffix = val.substr(val.lastIndexOf('.') + 1, val.length);
			suffix = suffix.toLowerCase();
			if (suffix == 'png' || suffix == 'gif' || suffix == 'jpg' || suffix == 'jpeg') {
				var fd = new FormData(document.getElementById("uploadForm"));
				fd.append("name", "This is some extra data");
				$.ajax({
					url: "upload/img",
					type: "POST",
					data: fd,
					dataType: 'json',
					processData: false,  // tell jQuery not to process the data
					contentType: false,   // tell jQuery not to set contentType
					success: function(data) {
						if (data) {
							$.ajax({
								url: 'user/updatePhoto',
								type: 'post',
								data: JSON.stringify({
									photo: data.url
								}),
								dataType: 'json',
								success: function(d) {
									if (d.status) {
										Util.msg.show('提示信息', '头像更新成功!');
										$('#myPhoto').attr('src', data.url);
									} else {
										Util.msg.show('提示信息', '头像更新失败!', 'error');
									}
								}
							})
						} else {
							Util.msg.show('提示信息', data.msg);
						}
					},
					error: function(data) {
						Util.error(data);
					}
				});
			} else {
				Util.msg.show('错误提示', '请选择正确格式的图片!', 'error');
			}
		});
	});
});