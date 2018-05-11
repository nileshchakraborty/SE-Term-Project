<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<div id="image">
	<img src="${post.imageUrl}" />
</div>
<div id="audio">
	<audio autoplay>
		<source src="${post.audioUrl}" type="audio/webm" />
	</audio>
</div>

<div class="container" id="myText">

	<script>
		$(document).ready(function() {
			$("#savetextButton").on("click", function() {
				$("#textForm").submit();
			});
		});
	</script>
	<label>Write a text for the audio and the images :</label>
	<form id="textForm" method="post" action="analysetextpost">
		<input type="text" name="text" id="text" />
	</form>

	<button id="savetextButton">Save</button>

</div>
<%@ include file="includes/footer.jsp"%>
