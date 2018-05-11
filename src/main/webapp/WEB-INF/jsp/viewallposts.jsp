<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/admin-header.jsp"%>
<div class="container">
	<div class="row">
		<table class="table table-dark">
			<thead>
				<tr>
					<th scope="col">ID</th>


				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allpost}" var="post">
					<tr>

						<td><a href="/viewallimage?id=${post.postId}"><img
								src="${post.imageUrl}" class="img-responsive"></a></td>
						<td><a href="/deletepost?id=${post.postId }">Delete</a>
					</tr>

				</c:forEach>
			</tbody>
		</table>
	</div>

</div>
<%@ include file="includes/footer.jsp"%>
