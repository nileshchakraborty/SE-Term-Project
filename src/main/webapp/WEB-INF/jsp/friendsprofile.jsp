<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>
<div class="container">

	<div class="row">
		<div class="col-4">
			<img class="profileImage" alt="Profile Page Image"
				src="${fprofileImage}">
		</div>
		<div class="col-5">
			<label class="control-label"><strong><h4>${fname}
					</h4></strong></label><br />
			<p>${fdescription}</p>
			<br />
		</div>

		<div class="col">
			<form action="/viewfriendsfriend" method="post">
				<br /> <input type="hidden" value="${femail}" name="email"
					id="email"> <label>View Friends</label> <input
					type="submit" class="btn btn-submit">
			</form>
		</div>


		<div class="col">
			<div class="row">
				<c:forEach items="${fposts}" var="post">

					<div class="gallery_product col-lg-4 col-md-4 col-sm-4 col-xs-6">

						<a href="/viewfriendsimage?id=${post.postId}"><img
							src="${post.imageUrl }" class="img-responsive"></a>
					</div>

				</c:forEach>
			</div>

		</div>


		<!-- <table border="2" class="table">
			<tr class="row">
				<td rowspan="3" class="col-9"><img class="profileImage"
					alt="Profile Page Image" src="${image}" /></td>
				<td class="col-4"><label>Name: Nilesh Chakraborty</label><br /> <label>Description:
						Student at University at Albany.</label></td>
			</tr>

		</table>
-->


	</div>

	<%@ include file="includes/footer.jsp"%>