<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
	<c:forEach items="${obj.data}" var="r">
		<tr>
			<td>${r.time}</td>
			<td>${r.operate}</td>
		<tr>
	</c:forEach>
</table>
