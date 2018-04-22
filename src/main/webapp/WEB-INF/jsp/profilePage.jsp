<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/header.jsp" %> 
	<div class="container">

		<div class="row">
			<div class="col-4">
				<img class="profileImage" alt="Profile Page Image" src="${user.profileImage}">
			</div>
			<div class="col-5">
				<label class="control-label"><strong><h4>${user.name} </h4></strong></label><br> 
				<p>${user.description}</p>
			</div>
			
			<form action="/viewfriends" method="post">
				<input type="hidden" value="${user.email}" name="email" id="email">
				<label>View My Friends</label>
				<input type="submit" class="btn btn-submit">
			</form>
			
			
				<form method="post" action="/createprofile">
				 <input
					type="hidden" name="myEmail" value="${user.email}"/>
				
				<label>Edit Profile</label>
				
				<input type="submit" class="btn btn-submit">
			</form>
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
	
<%@ include file="includes/footer.jsp" %> 