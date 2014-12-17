define(['jquery'], function($) {
	var left = 50,
		top = 55,
		data = [],
		dataTemp = [],
		dataTrace = [],
		interval,
		width = 800,
		height = 600,
		imgData,
		imgDataTemp;
	$('body').on('touchmove', '#canvas', function(e) {
		e.preventDefault();
	});
	$('body').on('touchstart', '#myCanvas', function(e){
		var context = document.getElementById('myCanvas').getContext('2d');
		imgData = imgData || context.getImageData(0,0,width,height);
		var x = e.originalEvent.targetTouches[0].pageX - left;
		var y = e.originalEvent.targetTouches[0].pageY - top;
		data.push({x: -1, y: -1});
		dataTemp.push({x: -1, y: -1});
		data.push({x: x, y: y});
		dataTemp.push({x: x, y: y});
		dataTrace = [];
		dataTrace.push({x: x, y: y});
		imgDataTemp = context.getImageData(0,0,width,height);
		if (Util.isLive) {
			interval = interval || setInterval(function() {
				var trace = '';
				while (dataTemp.length > 0) {
					var d = dataTemp.shift();
					trace += d.x + ',' + d.y + ';';
				}
				if (trace != '') {
					$.ajax({
						url: 'cache/point',
						type: 'post',
						data: JSON.stringify({
							data: trace,
							eid: $('#exercise').data('id')
						}),
						error: function(data) {
							Util.error(data);
						}
					})
				}
			}, 50);
		}
	});
	$('body').on('touchmove', '#myCanvas', function(e){
		var context = document.getElementById('myCanvas').getContext('2d');
		var x = e.originalEvent.targetTouches[0].pageX - left;
		var y = e.originalEvent.targetTouches[0].pageY - top;
		data.push({x: x, y: y});
		dataTemp.push({x: x, y: y});
		dataTrace.push({x: x, y: y});
		updateTrace(context);
	});
	
	$('body').on('touchend', '#myCanvas', function(e){
		var context = document.getElementById('myCanvas').getContext('2d');
		data.push({x: -1, y: -2});
		dataTemp.push({x: -1, y: -2});
	});
	$('body').on('click', '.c_toolbar .btn.clear', function(e) {
		var context = document.getElementById('myCanvas').getContext('2d');
		e.preventDefault();
		e.stopPropagation();
		data.push({x: -1, y: -3});
		dataTemp.push({x: -1, y: -3});
		context.putImageData(imgData, 0, 0);
	});
	$('body').on('click', '.c_toolbar .btn.back', function() {
		$('.e_canvas').empty();
		$('#fullIcon').removeClass('unclickable');
		clearData();
	});

	$('body').on('click', '.c_toolbar .btn.ok', function(e) {
		e.preventDefault();
		e.stopPropagation();
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
										eType: $('#exercise').data('type'),
										epId: $('#exercise').data('exercisePackageId')
									},
									dataType: 'json',
									success: function(d3) {
										if (d3.status) {
											$('.e_canvas').empty();
											$('#fullIcon').removeClass('unclickable');
											$('.e_my_write').attr('src', d.url).show();
											clearData();
										} else {
											Util.msg.show('错误提示', '保存图片失败!', 'error');
										}
									},
									error: function(data) {
										Util.error(data);
									}
								})
							} else {
								Util.msg.show('错误提示', '保存图片失败!', 'error');
							}
						},
						error: function(data) {
							Util.error(data);
						}
					})
				} else {
					Util.msg.show('错误提示', '保存图片失败!', 'error');
				}
			},
			error: function(data) {
				Util.error(data);
			}
		});
	});
	function clearData() {
		data = [];
		dataTemp = [];
		dataTrace = [];
		clearInterval(interval);
		imgData = null;
		imgDataTemp = null;
	}
	
	function updateTrace(context) {
		context.beginPath();
		context.putImageData(imgDataTemp, 0, 0);
		var controls = [], trace = dataTrace;
		for (var i = 0; i < trace.length; i++) {
			controls = controls.concat(drawControl(trace, i));
		}
		for (var i = 1; i < trace.length; i++) {
			context.moveTo(trace[i - 1].x, trace[i - 1].y);
			context.bezierCurveTo(controls[i * 2 - 1].x,
					controls[i * 2 - 1].y, controls[i * 2].x,
					controls[i * 2].y, trace[i].x, trace[i].y);
		}
		context.moveTo(trace[trace.length - 1].x,
				trace[trace.length - 1].y);
		context.closePath();
		context.stroke();
	}

	function drawControl(trace, n) {
		var point = [];
		point.push({
			x : 0,
			y : 0
		});
		point.push({
			x : 0,
			y : 0
		});
		if (n == 0) {
			point[0] = trace[0];
		} else {
			point[0] = Average(trace[n - 1], trace[n]);
		}
		if (n == trace.length - 1) {
			point[1] = trace[trace.length - 1];
		} else {
			point[1] = Average(trace[n], trace[n + 1]);
		}
		var ave = Average(point[0], point[1]);
		var sh = Sub(trace[n], ave);
		point[0] = Mul(Add(point[0], sh), trace[n], 0.6);
		point[1] = Mul(Add(point[1], sh), trace[n], 0.6);
		return point;
	}

	function Average(a, b) {
		return {
			x : (a.x + b.x) / 2,
			y : (a.y + b.y) / 2
		};
	}
	function Add(a, b) {
		return {
			x : a.x + b.x,
			y : a.y + b.y
		};
	}
	function Sub(a, b) {
		return {
			x : a.x - b.x,
			y : a.y - b.y
		};
	}
	function Mul(a, b, d) {
		var temp = Sub(a, b);
		temp = {
			x : temp.x * d,
			y : temp.y * d
		}
		return Add(b, temp);
	}
});