define(['jquery'], function($) {
	$('body').on('click', '#updatePwd', function() {
		var pwd = $('#info').find('.password').val();
		$.ajax({
			url: 'user/updatePwd',
			type: 'post',
			data: JSON.stringify({
				pwd: pwd
			}),
			dataType: 'json',
			success: function(data) {
				if (data.status) {
					Util.msg.show('提示信息', '密码更新成功!');
				} else {
					Util.msg.show('<span style="color: red">提示信息</span>', '密码更新失败!');
				}
			}
		});
	});
});