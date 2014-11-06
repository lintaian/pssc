define(['jquery'], function($) {
	var left = 50,
		top = 55,
		data = [],
		dataTemp = [],
		interval,
		tempX,
		tempY;
	$('body').on('touchmove', '#canvas', function(e) {
		e.preventDefault();
	})
	$('body').on('touchstart', '#myCanvas', function(e){
		var context = document.getElementById('myCanvas').getContext('2d');
		context.beginPath();
		var x = e.originalEvent.targetTouches[0].pageX - left;
		var y = e.originalEvent.targetTouches[0].pageY - top;
		context.moveTo(x, y);
		data.push({x: -1, y: -1});
		dataTemp.push({x: -1, y: -1});
		data.push({x: x, y: y});
		dataTemp.push({x: x, y: y});
		interval = interval || setInterval(function() {
			var trace = '';
			while (dataTemp.length > 0) {
				var d = dataTemp.shift();
				trace += d.x + ',' + d.y + ';';
			}
			if (trace != '') {
				$.ajax({
					url: 'cache',
					type: 'post',
					data: JSON.stringify({
						data: trace
					}),
					dataType: 'json',
					success: function(data) {
						
					}
				})
			}
		}, 50);
	})
	$('body').on('touchmove', '#myCanvas', function(e){
		var context = document.getElementById('myCanvas').getContext('2d');
		var x = e.originalEvent.targetTouches[0].pageX - left;
		var y = e.originalEvent.targetTouches[0].pageY - top;
		context.quadraticCurveTo(tempX, tempY, x, y);
		tempX = x;
		tempY = y;
		data.push({x: x, y: y});
		dataTemp.push({x: x, y: y});
		context.stroke();
	})
	$('body').on('touchend', '#myCanvas', function(e){
		var context = document.getElementById('myCanvas').getContext('2d');
		data.push({x: -1, y: -2});
		dataTemp.push({x: -1, y: -2});
		context.closePath();
	});
	$('body').on('click', '.c_toolbar .btn.clear', function(e) {
		e.preventDefault();
		e.stopPropagation();
		var context = document.getElementById('myCanvas').getContext('2d');
		context.clearRect(0, 0, 800, 600);
		var bgImg = $('.e_write').data('bgImg');
		if (bgImg) {
			img = new Image();
			img.src = bgImg;
			img.onload = function() {
				context.drawImage(img, 0, 0, 800, 600);
			}
		}
		data.push({x: -1, y: -3});
		dataTemp.push({x: -1, y: -3});
	});
	$('body').on('click', '.c_toolbar .btn.back', function() {
		$('.e_canvas').empty();
		$('#fullIcon').removeClass('unclickable');
	});
	$('body').on('click', '.c_toolbar .btn.ok', function(e) {
		e.preventDefault();
		e.stopPropagation();
		clearInterval(interval);
		var canvas = document.getElementById('myCanvas');
		$.ajax({
			url: 'upload/draw',
			type: 'post',
			data: JSON.stringify({
				data: canvas.toDataURL("image/png")
			}),
			dataType: 'json',
			success: function(d) {
				if (d.status) {
					var trace = '';
					for (var i = 0; i < data.length; i++) {
						trace += data[i].x;
						trace += ',';
						trace += data[i].y;
						if (i < data.length - 1) {
							trace += ';';
						}
					}
					$.ajax({
						url: 'exercise/canvas/imageTrace',
						type: 'get',
						data: {
							imageUrl: d.url,
							trace: trace
						},
						dataType: 'json',
						success: function(d2) {
							if (d2.status) {
								$.ajax({
									url: 'exercise/subjective',
									type: 'get',
									data: {
										answer: d.url,
										eId: $('#exercise').data('id'),
										eType: $('#exercise').data('type')
									},
									dataType: 'json',
									success: function(d3) {
										if (d3.status) {
											$('.e_canvas').empty();
											$('#fullIcon').removeClass('unclickable');
											$('.e_my_write').attr('src', d.url).show();
										} else {
											Util.msg.show('错误提示', '保存图片失败!', 'error');
										}
									}
								})
							} else {
								Util.msg.show('错误提示', '保存图片失败!', 'error');
							}
						}
					})
				} else {
					Util.msg.show('错误提示', '保存图片失败!', 'error');
				}
			}
		});
	});
});