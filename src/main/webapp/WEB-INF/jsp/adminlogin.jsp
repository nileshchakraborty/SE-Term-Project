<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="includes/header.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Login via site</h3>
				</div>
				<div class="panel-body">
					<form accept-charset="UTF-8" role="form">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="yourmail@example.com"
									name="email" type="text">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Password"
									name="password" type="password" value="">
							</div>
							<div class="checkbox">
								<label> <input name="remember" type="checkbox"
									value="Remember Me"> Remember Me
								</label>
							</div>
							<input class="btn btn-lg btn-success btn-block" type="submit"
								value="Login">
						</fieldset>
					</form>
					<hr />
					<center>
						<h4>OR</h4>
					</center>
					<input class="btn btn-lg btn-facebook btn-block" type="submit"
						value="Login via facebook">
				</div>
			</div>
		</div>
	</div>
</div>
	<%@ include file="includes/footer.jsp"%>