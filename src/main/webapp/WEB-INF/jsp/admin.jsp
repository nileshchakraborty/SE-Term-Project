<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/admin-header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col">
			<form method="get" action="viewalluser">
				<label>View All Users</label> <input type="submit"
					class="btn btn-submit">
			</form>

		</div>

		<div class="col">
			<form method="get" action="viewallpost">
				<label>View All Posts</label> <input type="submit"
					class="btn btn-submit">
			</form>

		</div>

	</div>
</div>

<%@ include file="includes/footer.jsp"%>
