<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/admin-header.jsp"%>
<div class="container">
	<div id="image">
		<img src="${specpost.imageUrl}" />
	</div>
	<div id="audio">
		<audio autoplay>
			<source src="${specpost.audioUrl}" type="audio/webm" />
		</audio>
	</div>
	<div id="text">
		<label>${specpost.text}</label>
	</div>

	<div id="comment">

		<div class="row">
			<table class="table table-dark">
				<thead>
					<tr>
						<th scope="col">Comment</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${comments}" var="comment">
						<tr>
							<td><c:out value="${comment.username}"></c:out> : <c:out
									value="${comment.comment}"></c:out></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="includes/footer.jsp"%>
