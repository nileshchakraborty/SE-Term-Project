<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>
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
						<td><c:out value="${comment.username}"></c:out> : <c:out value="${comment.comment}"></c:out></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	
	
	
	
	<div id="comment">
		
		<label>Leave a comment here</label>
		<form action="comment" method="post">
			<input type="hidden" id="postId" name="postId"
				value="${specpost.postId }" /> <input type="text" id="comment"
				name="comment" class="textbox" />
			<button type="submit" name="submit" id="submit">Send</button>
		</form>
	</div>
</div>

<%-- <div id="comment">
	<c:forEach items="comments" var="comment">
		<c:out value="${comment.comment}"></c:out>
	</c:forEach>
</div> --%>

<%@ include file="includes/footer.jsp"%>
