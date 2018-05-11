<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>

<c:forEach items="${notifications }" var="notification">
	<a href="/viewimage?id=${notification.postId }">${notification.receivedBy}
		you have notification from ${ notification.sentBy}
		${notification.type} </a>
	<br />
</c:forEach>
<%@ include file="includes/footer.jsp"%>
