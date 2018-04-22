<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile Creator</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<link href="image/logo.ico" rel="icon">
<link rel="stylesheet" type="text/css" href="css/custom.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">

			<a class="navbar-brand" href="#"> <img class="imageSize"
				alt="Brand" src="images/logo.ico"> Profile Creator
			</a>


		</div>
	</div>
	</nav>
	<div class="container">

		<div class="row">
			<div class="col-4">
				<img class="profileImage" alt="Profile Page Image" src="${image}">
			</div>
			<div class="col-5">
				<label class="control-label"><strong><h4>Nilesh
						Chakraborty</h4></strong></label><br> <label><h5> Student at University at
					Albany - MS CS, MCA</h5></label>
					<p><blockquote>I am a student at University at Albany, studying Master of Computer Science with Specialization in Machine Learning and Data Science and Minor in Software Engineering. I have 3 years of work experience in the field of Java and F.I.X. protocol. I have completed my Master of Computer Application from University of Mumbai, India. I love coding and solving complex algorithm problems.</<blockquote> 
					</p>
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


	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>