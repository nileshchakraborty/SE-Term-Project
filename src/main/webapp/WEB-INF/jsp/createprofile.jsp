<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/login-header.jsp"%>



<div class="container">
	<div class="row">
		<div class="col-md-10 ">

			<form action="/upload" method="POST" enctype="multipart/form-data">
				
					<fieldset>

						<!-- Form Name -->
						<legend>User profile form requirement</legend>

						<!-- Text input-->
						<div class="form-group">
							<label class="col-md-4 control-label" for="Name (Full name)">Name
								(Full name)</label>
							<div class="col-md-4">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-user"> </i>
									</div>
									<input id="myName" name="myName" type="text"
										placeholder="${user.name} " class="form-control input-md" value="${user.name} ">
								</div>


							</div>


						</div>

						<!-- File Button -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="Upload photo">Upload
								photo</label>
							<div class="col-md-4">
								<input id="file" name="file" class="input-file" type="file" value="${user.profileImage}">
							</div>
						</div>


						<!-- Text input-->
						<div class="form-group">
							<label class="col-md-4 control-label" for="Email Address">Email
								Address</label>
							<div class="col-md-4">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-envelope-o"></i>

									</div>
									<input id="myEmail" name="myEmail" type="text"
										placeholder="${user.email}" class="form-control input-md" value="${user.email} ">

								</div>

							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="description">Description</label>
							<div class="col-md-4">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-envelope-o"></i>

									</div>
									<input id="description" name="description" type="text"
										placeholder="${user.description}" class="form-control input-md" value="${user.description} ">

								</div>

							</div>
						</div>
						

						<div class="form-group">
							<label class="col-md-4 control-label"></label>
							<div class="col-md-4">
								<input type="submit" class="btn btn-success"
									placeholder=" Submit"> <span
									class="glyphicon glyphicon-thumbs-up"></span>
									<input
									type="reset" class="btn btn-danger"> <span
									class="glyphicon glyphicon-remove-sign" placeholder=" Clear"></span>
								
							</div>
						</div>

					</fieldset>
				</form>
		</div>
		<div class="col-md-2 hidden-xs">
			<img src="${user.profileImage}"
				class="img-responsive img-thumbnail ">
		</div>


	</div>
</div>



<%@ include file="includes/footer.jsp"%>
