<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp" %>
<h2>${friend.friendName }</h2>

<div class="container">
	
	<div class="row">
		<c:forEach items="${posts}" var="post">
 
			<div
				class="gallery_product col-lg-4 col-md-4 col-sm-4 col-xs-6">
				
				<a href="/viewimage?id=${post.postId}"><img src="${post.imageUrl }" class="img-responsive"></a>
			</div>

		</c:forEach>
	</div>
</div>
<%@ include file="includes/footer.jsp" %>  