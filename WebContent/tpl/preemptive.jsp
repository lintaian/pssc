<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="preemptive" data-id="${obj.p._id }">
	<div>${obj.p.title }</div>
	<div class="preemptive ${obj.pa != null ? 'on' : '' }"></div>
	<div>
		<span class="btn preemptive_finish">完成</span>
	</div>
</div>