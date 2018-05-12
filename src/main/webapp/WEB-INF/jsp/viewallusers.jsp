<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/admin-header.jsp"%>
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
				<c:forEach items="${alluser}" var="user">
					<tr>
						
						<td><a href="/viewusersprofile?id=${user.userId }">${user.userId }</a></td>
						<td><a href="/viewusersprofile?id=${user.userId }">${user.name }</a></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<%@ include file="includes/footer.jsp"%>
