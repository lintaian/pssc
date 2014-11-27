<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="preemptive" data-id="${obj.p._id }">
	<div>${obj.p.title }</div>
	<div class="preemptive ${obj.pa != null ? 'on' : '' }"></div>
	<div>
		<span class="btn preemptive_finish ${obj.pa == null ? 'hide' : '' }">完成</span>
	</div>
</div>
<script>
	$(function() {
		function loop() {
			setTimeout(function() {
				$.ajax({
					url: 'preemptive/access',
					type: 'get',
					data: {
						id: $('#preemptive').data('id')	
					},
					dataType: 'json',
					success: function(data) {
						if (data.status) {
							Util.preemptiveSuccess.show();
						} else {
							loop();					
						}
					}
				});
			}, 1000);
		}
		loop();
	});
</script>