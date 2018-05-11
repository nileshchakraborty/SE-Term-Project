<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/login-header.jsp" %>


<body>
<div class="container">
	<div class="row">
		<table class="table table-dark">
			<thead>
				<tr>
					<th scope="col">ID</th>
					<th scope="col">Name</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${friends}" var="friend">
					<tr>
						<td><a href="/viewfriendsprofile?id=${friend.friendId}">${friend.friendId}</a></td>
						<td><a href="/viewfriendsprofile?id=${friend.friendId}">${friend.friendName}</a></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@ include file="includes/footer.jsp" %>  