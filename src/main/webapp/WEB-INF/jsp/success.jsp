<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>
<div class="container">
	<div id="image">
		<img src="${post.imageUrl}" />
	</div>
	<div id="audio">
		<audio autoplay>
			<source src="${post.audioUrl}" type="audio/webm" />
		</audio>
	</div>
	<div id="text">
		<label>${post.text}</label>
	</div>
	<label>Post created successfully.</label>
	<form action="createpost" method="get">
		<button class="btn" id="submit">Create another post</button>
	</form>
</div>

<%@ include file="includes/footer.jsp"%>
